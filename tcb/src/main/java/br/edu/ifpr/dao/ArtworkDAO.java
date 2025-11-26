package br.edu.ifpr.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpr.model.Artwork;

public class ArtworkDAO {

    public void create(Artwork artwork) {
        String sql = "INSERT INTO TCB_lima_kortz.Artwork " +
                "(title, description, category, id_artist) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, artwork.getTitle());
            stmt.setString(2, artwork.getDescription());
            stmt.setString(3, artwork.getCategory());
            stmt.setInt(4, artwork.getIdArtist());

            stmt.executeUpdate();
            System.out.println("Artwork criada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao criar artwork: " + e.getMessage());
        }
    }

    public List<Artwork> readAll() {
        List<Artwork> artworks = new ArrayList<>();
        String sql = "SELECT * FROM TCB_lima_kortz.Artwork";

        try (Connection conn = ConnectionFactory.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Artwork a = new Artwork();

                a.setId(rs.getInt("id"));
                a.setTitle(rs.getString("title"));
                a.setDescription(rs.getString("description"));
                a.setCategory(rs.getString("category"));
                a.setIdArtist(rs.getInt("id_artist"));

                artworks.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar artworks: " + e.getMessage());
        }

        return artworks;
    }

    public void update(Artwork artwork) {
        String sql = "UPDATE TCB_lima_kortz.Artwork " +
                "SET title=?, description=?, category=?, id_artist=? " +
                "WHERE id=?";

        try (Connection conn = ConnectionFactory.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, artwork.getTitle());
            stmt.setString(2, artwork.getDescription());
            stmt.setString(3, artwork.getCategory());
            stmt.setInt(4, artwork.getIdArtist());
            stmt.setInt(5, artwork.getId());

            stmt.executeUpdate();
            System.out.println("Artwork atualizada!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar artwork: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM TCB_lima_kortz.Artwork WHERE id=?";

        try (Connection conn = ConnectionFactory.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Artwork deletada!");

        } catch (SQLException e) {
            System.out.println("Erro ao deletar artwork: " + e.getMessage());
        }
    }
}
