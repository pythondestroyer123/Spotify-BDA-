/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package mx.itson.spotify.authservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    // Método para registrar un nuevo usuario
    public boolean register(String email, String password) {
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        try (Connection conn = Database.getConnection()) {
            String sql = "INSERT INTO users (email, password_hash) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, hashed);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error en el registro: " + e.getMessage());
            return false;
        }
    }

    // Método para loguear un usuario
    public boolean login(String email, String password) {
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT password_hash FROM users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                return BCrypt.checkpw(password, storedHash);
            } else {
                return false; // usuario no encontrado
            }
        } catch (SQLException e) {
            System.out.println("Error en el login: " + e.getMessage());
            return false;
        }
    }
}