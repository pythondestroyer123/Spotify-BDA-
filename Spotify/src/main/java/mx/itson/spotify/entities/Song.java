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
public class Song {
    private int songId;
    private int albumId;
    private String songName;
    private int duration; // en segundos

    // Getters y setters
    public int getSongId() {
        return songId;
    }
    public void setSongId(int songId) {
        this.songId = songId;
    }
    public int getAlbumId() {
        return albumId;
    }
    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }
    public String getSongName() {
        return songName;
    }
    public void setSongName(String songName) {
        this.songName = songName;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
     public boolean crear(Song song) {
        String sql = "INSERT INTO Song (album_id, songName, duration) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, song.getAlbumId());
            ps.setString(2, song.getSongName());
            ps.setInt(3, song.getDuration());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        song.setSongId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al crear canción: " + e.getMessage());
        }
        return false;
    }

    // Obtener todas las canciones
    public List<Song> obtenerTodos() {
        List<Song> canciones = new ArrayList<>();
        String sql = "SELECT * FROM Song";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Song song = new Song();
                song.setSongId(rs.getInt("song_id"));
                song.setAlbumId(rs.getInt("album_id"));
                song.setSongName(rs.getString("songName"));
                song.setDuration(rs.getInt("duration"));
                canciones.add(song);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener canciones: " + e.getMessage());
        }

        return canciones;
    }

    // Obtener canción por ID
    public Song obtenerPorId(int id) {
        String sql = "SELECT * FROM Song WHERE song_id = ?";
        Song song = null;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    song = new Song();
                    song.setSongId(rs.getInt("song_id"));
                    song.setAlbumId(rs.getInt("album_id"));
                    song.setSongName(rs.getString("songName"));
                    song.setDuration(rs.getInt("duration"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener canción por ID: " + e.getMessage());
        }

        return song;
    }

    // Actualizar canción
    public boolean actualizar(Song song) {
        String sql = "UPDATE Song SET album_id = ?, songName = ?, duration = ? WHERE song_id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, song.getAlbumId());
            ps.setString(2, song.getSongName());
            ps.setInt(3, song.getDuration());
            ps.setInt(4, song.getSongId());

            int filasActualizadas = ps.executeUpdate();

            return filasActualizadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar canción: " + e.getMessage());
        }

        return false;
    }

    // Eliminar canción
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Song WHERE song_id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filasEliminadas = ps.executeUpdate();

            return filasEliminadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar canción: " + e.getMessage());
        }

        return false;
    }
}


