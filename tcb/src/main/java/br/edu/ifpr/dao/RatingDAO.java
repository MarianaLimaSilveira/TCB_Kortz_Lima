package br.edu.ifpr.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpr.model.Rating;

public class RatingDAO {

   public void create(Rating rating) {
        // CORREÇÃO: Nomes das colunas batendo com o banco (id_artwork, id_user, id_exhibition)
        String sql = "INSERT INTO reviews (id_artwork, id_exhibition, id_user, note, text) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (rating.getArtworkId() != null) {
                stmt.setLong(1, rating.getArtworkId());
            } else {
                stmt.setNull(1, java.sql.Types.BIGINT);
            }

            if (rating.getExhibitionId() != null) {
                stmt.setLong(2, rating.getExhibitionId());
            } else {
                stmt.setNull(2, java.sql.Types.BIGINT);
            }

            stmt.setLong(3, rating.getUserId());
            stmt.setInt(4, rating.getNote());
            stmt.setString(5, rating.getText());

            stmt.executeUpdate();
            System.out.println("Avaliação criada!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar avaliação: " + e.getMessage());
        }
    }

    public List<Rating> readAll() {
        List<Rating> ratings = new ArrayList<>();
        String sql = "SELECT * FROM reviews";

        try (Connection conn = ConnectionFactory.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Rating r = new Rating();

                r.setId(rs.getLong("id"));

                Long artworkId = rs.getLong("id_artwork");
                r.setArtworkId(rs.wasNull() ? null : artworkId);

                Long exhibitionId = rs.getLong("id_exhibition");
                r.setExhibitionId(rs.wasNull() ? null : exhibitionId);

                r.setUserId(rs.getLong("id_user"));
                r.setNote(rs.getInt("note"));
                r.setText(rs.getString("text"));

                ratings.add(r);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar ratings: " + e.getMessage());
        }

        return ratings;
    }

    public void update(Rating rating) {
        String sql = "UPDATE reviews " +
                "SET id_artwork=?, id_exhibition=?, id_user=?, note=?, text=? " +
                "WHERE id=?";

        try (Connection conn = ConnectionFactory.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (rating.getArtworkId() != null) {
                stmt.setLong(1, rating.getArtworkId());
            } else {
                stmt.setNull(1, Types.BIGINT);
            }

            if (rating.getExhibitionId() != null) {
                stmt.setLong(2, rating.getExhibitionId());
            } else {
                stmt.setNull(2, Types.BIGINT);
            }

            stmt.setLong(3, rating.getUserId());
            stmt.setInt(4, rating.getNote());
            stmt.setString(5, rating.getText());
            stmt.setLong(6, rating.getId());

            stmt.executeUpdate();
            System.out.println("Rating atualizada!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar rating: " + e.getMessage());
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM reviews WHERE id=?";

        try (Connection conn = ConnectionFactory.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

            System.out.println("Rating deletada!");

        } catch (SQLException e) {
            System.out.println("Erro ao deletar rating: " + e.getMessage());
        }
    }

    public boolean existsByUserId(int userId) {
        String sql = "SELECT 1 FROM reviews WHERE id_user = ? LIMIT 1";
        try (Connection conn = ConnectionFactory.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }
}