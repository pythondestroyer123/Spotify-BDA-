/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.spotify.authservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/tu_base"; // <-- Cambia tu_base por el nombre real de tu BD
    private static final String USER = "root"; // <-- tu usuario de MySQL
    private static final String PASSWORD = "tu_contraseña"; // <-- tu contraseña de MySQL

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
