package br.edu.ifpr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpr.model.Artwork;

public class ArtworkDAO {

    public void create(Artwork artwork) {
        String sql = "INSERT INTO artworks (title, description, id_category, id_user) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, artwork.getTitle());
            stmt.setString(2, artwork.getDescription());
            stmt.setLong(3, artwork.getIdCategory());
            stmt.setLong(4, artwork.getIdUser());

            stmt.executeUpdate();
            System.out.println("Artwork criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar artwork: " + e.getMessage());
        }
    }

    public List<Artwork> readAll() {
        List<Artwork> artworks = new ArrayList<>();
        String sql = "SELECT * FROM artworks";

        try (Connection conn = ConnectionFactory.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Artwork a = new Artwork();
                a.setId(rs.getInt("id"));
                a.setTitle(rs.getString("title"));
                a.setDescription(rs.getString("description"));
                a.setIdCategory(rs.getLong("id_category"));
                a.setIdUser(rs.getLong("id_user"));
                artworks.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar artworks: " + e.getMessage());
        }

        return artworks;
    }

    public void update(Artwork artwork) {
        String sql = "UPDATE artworks " +
                "SET title=?, description=?, id_category=?, id_user=? " +
                "WHERE id=?";

        try (Connection conn = ConnectionFactory.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, artwork.getTitle());
            stmt.setString(2, artwork.getDescription());
            stmt.setLong(3, artwork.getIdCategory());
            stmt.setLong(4, artwork.getIdUser());
            stmt.setLong(5, artwork.getId());

            stmt.executeUpdate();
            System.out.println("Artwork atualizada!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar artwork: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM artworks WHERE id=?";

        try (Connection conn = ConnectionFactory.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Artwork deletada!");

        } catch (SQLException e) {
            System.out.println("Erro ao deletar artwork: " + e.getMessage());
        }
    }

    public Artwork findById(int id) {
        String sql = "SELECT * FROM artworks WHERE id = ?";
        Artwork artwork = null;

        try (Connection conn = ConnectionFactory.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                artwork = new Artwork();
                artwork.setId(rs.getInt("id"));
                artwork.setTitle(rs.getString("title"));
                artwork.setDescription(rs.getString("description"));
                artwork.setIdCategory(rs.getLong("id_category"));
                // IMPORTANTE: Aqui recuperamos o ID do usuário original
                artwork.setIdUser(rs.getLong("id_user"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar artwork: " + e.getMessage());
        }
        return artwork;
    }
}
