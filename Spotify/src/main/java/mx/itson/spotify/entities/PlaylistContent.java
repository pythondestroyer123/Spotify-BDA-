/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.spotify.entities;
///////
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.itson.spotify.db.ConnectionDB;

/**
 *
 * @author emili
 */
public class PlaylistContent {

    // Agregar canción a playlist
    public boolean agregarCancion(int playlistId, int songId) {
        String sql = "INSERT INTO Playlist_Song (playlist_id, song_id) VALUES (?, ?)";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, playlistId);
            ps.setInt(2, songId);

            int filasInsertadas = ps.executeUpdate();

            return filasInsertadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar canción a playlist: " + e.getMessage());
        }
        return false;
    }

    // Remover canción de playlist
    public boolean removerCancion(int playlistId, int songId) {
        String sql = "DELETE FROM Playlist_Song WHERE playlist_id = ? AND song_id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, playlistId);
            ps.setInt(2, songId);

            int filasEliminadas = ps.executeUpdate();

            return filasEliminadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al remover canción de playlist: " + e.getMessage());
        }

        return false;
    }

    // Obtener canciones de una playlist
    public List<Integer> obtenerCancionesPorPlaylist(int playlistId) {
        List<Integer> canciones = new ArrayList<>();
        String sql = "SELECT song_id FROM Playlist_Song WHERE playlist_id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, playlistId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    canciones.add(rs.getInt("song_id"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener canciones de playlist: " + e.getMessage());
        }

        return canciones;
    }
}

