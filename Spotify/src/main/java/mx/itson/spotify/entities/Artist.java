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
public class Artist {
    private int artistId;
    private String artistName;
    private String genre;
    private int followers;

    // Getters y setters
    public int getArtistId() {
        return artistId;
    }
    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }
    public String getArtistName() {
        return artistName;
    }
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public int getFollowers() {
        return followers;
    }
    public void setFollowers(int followers) {
        this.followers = followers;
    }


    public boolean crear(Artist artist) {
        String sql = "INSERT INTO Artist (artistName, genre, followers) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, artist.getArtistName());
            ps.setString(2, artist.getGenre());
            ps.setInt(3, artist.getFollowers());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        artist.setArtistId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al crear artista: " + e.getMessage());
        }
        return false;
    }

    // Leer todos los artistas
    public List<Artist> obtenerTodos() {
        List<Artist> artistas = new ArrayList<>();
        String sql = "SELECT * FROM Artist";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Artist a = new Artist();
                a.setArtistId(rs.getInt("artist_id"));
                a.setArtistName(rs.getString("artistName"));
                a.setGenre(rs.getString("genre"));
                a.setFollowers(rs.getInt("followers"));
                artistas.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener artistas: " + e.getMessage());
        }

        return artistas;
    }

    // Leer artista por ID
    public Artist obtenerPorId(int id) {
        String sql = "SELECT * FROM Artist WHERE artist_id = ?";
        Artist artist = null;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    artist = new Artist();
                    artist.setArtistId(rs.getInt("artist_id"));
                    artist.setArtistName(rs.getString("artistName"));
                    artist.setGenre(rs.getString("genre"));
                    artist.setFollowers(rs.getInt("followers"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener artista por ID: " + e.getMessage());
        }

        return artist;
    }

    // Actualizar artista
    public boolean actualizar(Artist artist) {
        String sql = "UPDATE Artist SET artistName = ?, genre = ?, followers = ? WHERE artist_id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, artist.getArtistName());
            ps.setString(2, artist.getGenre());
            ps.setInt(3, artist.getFollowers());
            ps.setInt(4, artist.getArtistId());

            int filasActualizadas = ps.executeUpdate();

            return filasActualizadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar artista: " + e.getMessage());
        }

        return false;
    }

    // Eliminar artista
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Artist WHERE artist_id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filasEliminadas = ps.executeUpdate();

            return filasEliminadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar artista: " + e.getMessage());
        }

        return false;
    }
}
