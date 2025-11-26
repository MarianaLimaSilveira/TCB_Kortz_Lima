package br.edu.ifpr.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpr.model.Rating;

public class RatingDAO {

    public void create(Rating rating) {
        String sql = "INSERT INTO TCB_lima_kortz.Rating " +
                "(artwork_id, exhibition_id, user_id, note, text) " +
                "VALUES (?, ?, ?, ?, ?)";

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

            stmt.executeUpdate();
            System.out.println("Rating criada com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao criar rating: " + e.getMessage());
        }
    }

    public List<Rating> readAll() {
        List<Rating> ratings = new ArrayList<>();
        String sql = "SELECT * FROM TCB_lima_kortz.Rating";

        try (Connection conn = ConnectionFactory.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Rating r = new Rating();

                r.setId(rs.getLong("id"));

                Long artworkId = rs.getLong("artwork_id");
                r.setArtworkId(rs.wasNull() ? null : artworkId);

                Long exhibitionId = rs.getLong("exhibition_id");
                r.setExhibitionId(rs.wasNull() ? null : exhibitionId);

                r.setUserId(rs.getLong("user_id"));
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
        String sql = "UPDATE TCB_lima_kortz.Rating " +
                "SET artwork_id=?, exhibition_id=?, user_id=?, note=?, text=? " +
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
        String sql = "DELETE FROM TCB_lima_kortz.Rating WHERE id=?";

        try (Connection conn = ConnectionFactory.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

            System.out.println("Rating deletada!");

        } catch (SQLException e) {
            System.out.println("Erro ao deletar rating: " + e.getMessage());
        }
    }
}
