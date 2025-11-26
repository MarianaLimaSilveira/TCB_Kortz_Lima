package br.edu.ifpr.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpr.model.ExhibitionArtwork;

public class ExhibitionArtworkDAO {

    private Connection conn;

    public ExhibitionArtworkDAO(Connection conn) {
        this.conn = conn;
    }

    public void create(ExhibitionArtwork ea) {
        String sql = "INSERT INTO exhibition_artwork (exhibition_id, artwork_id) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, ea.getExhibitionId());
            stmt.setLong(2, ea.getArtworkId());
            stmt.executeUpdate();
            System.out.println("ExhibitionArtwork inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir ExhibitionArtwork: " + e.getMessage());
        }
    }

    public List<ExhibitionArtwork> readAll() {
        List<ExhibitionArtwork> lista = new ArrayList<>();
        String sql = "SELECT id, exhibition_id, artwork_id FROM exhibition_artwork";

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ExhibitionArtwork ea = new ExhibitionArtwork();
                ea.setId(rs.getLong("id"));
                ea.setExhibitionId(rs.getLong("exhibition_id"));
                ea.setArtworkId(rs.getLong("artwork_id"));

                lista.add(ea);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar ExhibitionArtwork: " + e.getMessage());
        }
        return lista;
    }

    public void update(ExhibitionArtwork ea) {
        String sql = "UPDATE exhibition_artwork SET exhibition_id = ?, artwork_id = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, ea.getExhibitionId());
            stmt.setLong(2, ea.getArtworkId());
            stmt.setLong(3, ea.getId());

            stmt.executeUpdate();
            System.out.println("ExhibitionArtwork atualizado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar ExhibitionArtwork: " + e.getMessage());
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM exhibition_artwork WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            System.out.println("ExhibitionArtwork removido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao remover ExhibitionArtwork: " + e.getMessage());
        }
    }
}
