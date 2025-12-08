package br.edu.ifpr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import br.edu.ifpr.model.Exhibition;

public class ExhibitionDAO {

    public void create(Exhibition exhibition) {
        String sql = "INSERT INTO exhibitions " +
                "(id_creator, name, theme, description, start_date, end_date) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, exhibition.getIdCreator());
            stmt.setString(2, exhibition.getName());
            stmt.setString(3, exhibition.getTheme());
            stmt.setString(4, exhibition.getDescription());
            stmt.setString(5, exhibition.getStartDate());
            stmt.setString(6, exhibition.getEndDate());

            stmt.executeUpdate();
            System.out.println("Exibição criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar exibição: " + e.getMessage());
        }
    }

    public List<Exhibition> readAll() {
        List<Exhibition> exhibitions = new ArrayList<>();
        String sql = "SELECT * FROM exhibitions";

        try (Connection conn = ConnectionFactory.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Exhibition ex = new Exhibition();
                ex.setId(rs.getInt("id"));
                ex.setIdCreator(rs.getInt("id_creator"));
                ex.setName(rs.getString("name"));
                ex.setTheme(rs.getString("theme"));
                ex.setDescription(rs.getString("description"));
                ex.setStartDate(rs.getString("start_date"));
                ex.setEndDate(rs.getString("end_date"));
                exhibitions.add(ex);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar exibições: " + e.getMessage());
        }
        return exhibitions;
    }

    public void update(Exhibition exhibition) {
        String sql = "UPDATE exhibitions " +
                "SET id_creator=?, name=?, theme=?, description=?, start_date=?, end_date=? " +
                "WHERE id=?";

        try (Connection conn = ConnectionFactory.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, exhibition.getIdCreator());
            stmt.setString(2, exhibition.getName());
            stmt.setString(3, exhibition.getTheme());
            stmt.setString(4, exhibition.getDescription());
            stmt.setString(5, exhibition.getStartDate());
            stmt.setString(6, exhibition.getEndDate());
            stmt.setLong(7, exhibition.getId());

            stmt.executeUpdate();
            System.out.println("Exibição atualizada!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar exibição: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM exhibitions WHERE id=?";

        try (Connection conn = ConnectionFactory.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Exibição deletada!");

        } catch (SQLException e) {
            System.out.println("Erro ao deletar exibição: " + e.getMessage());
        }
    }
}