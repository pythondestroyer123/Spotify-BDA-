/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.spotify.dao;

import mx.itson.spotify.db.ConnectionDB;
import mx.itson.spotify.model.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emiliano
 */
public class UserDAO {
    
    public static User findByUsername(String username) {
        ConnectionDB conexion = new ConnectionDB();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        User user = null;
        
        try (Connection cnx = conexion.getConnection()) 
        {
            String query = "select user_id, username, password, active, salt, nombres, apellidos, correo, celular from users where username = ? and active = 1 limit 1";
            preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, username);
            rs = preparedStatement.executeQuery();
            
            if(rs.next()) {
                
                int userIdU = rs.getInt("user_id");
                String usernameU = rs.getString("username");
                String passwordU = rs.getString("password");
                boolean activeU = rs.getBoolean("active");
                String saltU = rs.getString("salt");
                String nombresU = rs.getString("nombres");
                String apellidosU = rs.getString("apellidos");
                String correoU = rs.getString("correo");
                String celularU = rs.getString("celular");
                
                user = new User();
                user.setUserId(userIdU);
                user.setUsername(usernameU);
                user.setPassword(passwordU);
                user.setActive(activeU);
                user.setSalt(saltU);
                user.setNombres(nombresU);
                user.setApellidos(apellidosU);
                user.setCorreo(correoU);
                user.setCelular(celularU);
                
            }
        }catch(Exception ex) {
            System.out.println(ex.toString());
        }
        
        return user;
    }
    
    public static User register(User user) {
        ConnectionDB conexion = new ConnectionDB();
        try(Connection cnx = conexion.getConnection();)
        {
            try{
                //Para evitar que se haga commit en cada transaccion
                if(cnx.getAutoCommit()){
                    cnx.setAutoCommit(false);
                }
                
                PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;
                
                String salt = generateSalt(32);
                String password = hashPassword(user.getPassword(), salt);
                
                String queryInsertUser = "insert into users (username, password, active, salt, nombres, apellidos, correo, celular) values (?, ?, ?, ?, ?, ?, ?, ?)";
                preparedStatement = cnx.prepareStatement(queryInsertUser);
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, password);
                preparedStatement.setBoolean(3, true);
                preparedStatement.setString(4, salt);
                preparedStatement.setString(5, user.getNombres());
                preparedStatement.setString(6, user.getApellidos());
                preparedStatement.setString(7, user.getCorreo());
                preparedStatement.setString(8, user.getCelular());                
                preparedStatement.executeUpdate();
                
                String queryLastID = "select LAST_INSERT_ID()";
                preparedStatement = cnx.prepareStatement(queryLastID);
                resultSet = preparedStatement.executeQuery();
                int idUser = 0;
                if(resultSet.next()) {
                    idUser = resultSet.getInt(1);
                }
                
                cnx.commit();
                
                user.setUserId( idUser );
                return user;
                
            }catch(Exception ex) {
                System.out.println(ex.toString());
                cnx.rollback();
            }
            
        }catch(SQLException ex) {
            System.out.println(ex.toString());
        }
        
        return null;
    }
    
    // Genera un salt aleatorio
    public static String generateSalt(int length) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hashea la contraseña con el salt usando SHA-256
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String saltedPassword = salt + password;
        byte[] hashBytes = digest.digest(saltedPassword.getBytes());
        return Base64.getEncoder().encodeToString(hashBytes);
    }

    public static boolean validatePassoword(User userFind, String password) {
        try {
            String passHash = hashPassword(password, userFind.getSalt());
            if(passHash.equals(userFind.getPassword())) {
                return true;
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
