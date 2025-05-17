/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.spotify.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.itson.spotify.db.ConnectionDB;

/**
 *
 * @author emili
 */
public class Playlist {
    private int playlistId;
    private int userId;
    private String playlistName;
    private String description;

    // Getters y setters
    public int getPlaylistId() {
        return playlistId;
    }
    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getPlaylistName() {
        return playlistName;
    }
    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public class PlaylistItem {
    private int id;
    private String name;

    public PlaylistItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return name;
    }
}
    public class PlaylistDAO {

    // Crear playlist
    public boolean crear(Playlist playlist) {
        String sql = "INSERT INTO Playlist (user_id, playlistName, description) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, playlist.getUserId());
            ps.setString(2, playlist.getPlaylistName());
            ps.setString(3, playlist.getDescription());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        playlist.setPlaylistId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al crear playlist: " + e.getMessage());
        }
        return false;
    }

    // Obtener todas las playlists
    public List<Playlist> obtenerTodos() {
        List<Playlist> playlists = new ArrayList<>();
        String sql = "SELECT * FROM Playlist";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Playlist p = new Playlist();
                p.setPlaylistId(rs.getInt("playlist_id"));
                p.setUserId(rs.getInt("user_id"));
                p.setPlaylistName(rs.getString("playlistName"));
                p.setDescription(rs.getString("description"));
                playlists.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener playlists: " + e.getMessage());
        }

        return playlists;
    }

    // Obtener playlist por ID
    public Playlist obtenerPorId(int id) {
        String sql = "SELECT * FROM Playlist WHERE playlist_id = ?";
        Playlist playlist = null;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    playlist = new Playlist();
                    playlist.setPlaylistId(rs.getInt("playlist_id"));
                    playlist.setUserId(rs.getInt("user_id"));
                    playlist.setPlaylistName(rs.getString("playlistName"));
                    playlist.setDescription(rs.getString("description"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener playlist por ID: " + e.getMessage());
        }

        return playlist;
    }

    // Actualizar playlist
    public boolean actualizar(Playlist playlist) {
        String sql = "UPDATE Playlist SET user_id = ?, playlistName = ?, description = ? WHERE playlist_id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, playlist.getUserId());
            ps.setString(2, playlist.getPlaylistName());
            ps.setString(3, playlist.getDescription());
            ps.setInt(4, playlist.getPlaylistId());

            int filasActualizadas = ps.executeUpdate();

            return filasActualizadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar playlist: " + e.getMessage());
        }

        return false;
    }

    // Eliminar playlist
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Playlist WHERE playlist_id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filasEliminadas = ps.executeUpdate();

            return filasEliminadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar playlist: " + e.getMessage());
        }

        return false;
    }
}

}
