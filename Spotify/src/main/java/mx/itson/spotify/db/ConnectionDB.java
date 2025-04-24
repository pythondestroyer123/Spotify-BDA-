/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.spotify.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Emiliano
 */
public class ConnectionDB {
    
    private static final String URL = "jdbc:mariadb://localhost:3306/spotify";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";
    
    public Connection getConnection() {
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if(connection != null) {
                System.out.println("Conexión exitosa");
            }
        }catch(SQLException ex) {
            System.out.println("Error in connection: " + ex.toString());
        }
        return connection;
    }
    
}
