/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.spotify.services;

import mx.itson.spotify.db.ConnectionDB;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;


public class UserService {

    // Verifica si el username ya existe en la base de datos
    public boolean checkUsernameExists(String username) {
        boolean exists = false;
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    exists = rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return exists;
    }

    // Guarda un usuario nuevo en la base de datos con salt y contraseña hasheada
    public boolean saveUser(String nombres, String apellidos, String username, String password, String correo, String celular) {
        boolean saved = false;
        String sql = "INSERT INTO users (nombres, apellidos, username, password, salt, correo, celular) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Generar un salt aleatorio
        String salt = generateSalt();
        if (salt == null) {
            return false;
        }

        // Hashear la contraseña con el salt
        String hashedPassword = hashPasswordWithSalt(password, salt);
        if (hashedPassword == null) {
            return false; // Error al hashear la contraseña
        }

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombres);
            ps.setString(2, apellidos);
            ps.setString(3, username);
            ps.setString(4, hashedPassword);
            ps.setString(5, salt);
            ps.setString(6, correo);
            ps.setString(7, celular);

            int rows = ps.executeUpdate();
            saved = rows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return saved;
    }

    // Valida las credenciales del usuario en el login
    public boolean validateLogin(String username, String password) {
        String sql = "SELECT password, salt FROM users WHERE username = ? AND active = 1";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    String salt = rs.getString("salt");

                    // Hashear la contraseña ingresada con el salt guardado
                    String hashedInput = hashPasswordWithSalt(password, salt);

                    // Comparar hashes
                    return storedHash.equals(hashedInput);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
        
        
        
        
        
        
        
        
        
    }

    // Genera un salt aleatorio codificado en Base64
    private String generateSalt() {
        try {
            SecureRandom sr = SecureRandom.getInstanceStrong();
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return Base64.getEncoder().encodeToString(salt);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Hashea la contraseña concatenada con el salt usando SHA-256
    private String hashPasswordWithSalt(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwordWithSalt = password + salt;
            byte[] hashedBytes = md.digest(passwordWithSalt.getBytes());

            // Convierte a hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}