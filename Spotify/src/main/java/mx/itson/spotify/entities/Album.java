/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.spotify.entities;

import java.sql.Connection;
import java.sql.Date;
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
public class Album {
    private int albumId;
    private int artistId;
    private String albumName;
    private Date albumDate;

    // Getters y setters
    public int getAlbumId() {
        return albumId;
    }
    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }
    public int getArtistId() {
        return artistId;
    }
    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }
    public String getAlbumName() {
        return albumName;
    }
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
    public Date getAlbumDate() {
        return albumDate;
    }
    public void setAlbumDate(Date albumDate) {
        this.albumDate = albumDate;
    }

public boolean crear(Album album) {
        String sql = "INSERT INTO Album (artist_id, albumName, albumDate) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, album.getArtistId());
            ps.setString(2, album.getAlbumName());
            ps.setDate(3, album.getAlbumDate());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        album.setAlbumId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al crear álbum: " + e.getMessage());
        }
        return false;
    }

    // Obtener todos los álbumes
    public List<Album> obtenerTodos() {
        List<Album> albums = new ArrayList<>();
        String sql = "SELECT * FROM Album";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Album album = new Album();
                album.setAlbumId(rs.getInt("album_id"));
                album.setArtistId(rs.getInt("artist_id"));
                album.setAlbumName(rs.getString("albumName"));
                album.setAlbumDate(rs.getDate("albumDate"));
                albums.add(album);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener álbumes: " + e.getMessage());
        }

        return albums;
    }

    // Obtener álbum por ID
    public Album obtenerPorId(int id) {
        String sql = "SELECT * FROM Album WHERE album_id = ?";
        Album album = null;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    album = new Album();
                    album.setAlbumId(rs.getInt("album_id"));
                    album.setArtistId(rs.getInt("artist_id"));
                    album.setAlbumName(rs.getString("albumName"));
                    album.setAlbumDate(rs.getDate("albumDate"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener álbum por ID: " + e.getMessage());
        }

        return album;
    }

    // Actualizar álbum
    public boolean actualizar(Album album) {
        String sql = "UPDATE Album SET artist_id = ?, albumName = ?, albumDate = ? WHERE album_id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, album.getArtistId());
            ps.setString(2, album.getAlbumName());
            ps.setDate(3, album.getAlbumDate());
            ps.setInt(4, album.getAlbumId());

            int filasActualizadas = ps.executeUpdate();

            return filasActualizadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar álbum: " + e.getMessage());
        }

        return false;
    }

    // Eliminar álbum
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Album WHERE album_id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filasEliminadas = ps.executeUpdate();

            return filasEliminadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar álbum: " + e.getMessage());
        }

        return false;
    }
}
