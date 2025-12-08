package br.edu.ifpr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import br.edu.ifpr.model.ExhibitionArtwork;

public class ExhibitionArtworkDAO {

    private Connection conn;

    // Recebe a conexão via construtor para permitir transações ou uso compartilhado
    // (Importante para evitar abrir e fechar conexões repetidamente no loop de vincular)
    public ExhibitionArtworkDAO(Connection conn) {
        this.conn = conn;
    }

    public void create(ExhibitionArtwork ea) {
        String sql = "INSERT INTO exhibition_artwork (id_exhibition, id_artwork) VALUES (?, ?)";

        // Note que usamos 'this.conn' e NÃO fechamos a conexão aqui, 
        // quem chamou o DAO (Controller) é responsável por fechar.
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, ea.getExhibitionId());
            stmt.setLong(2, ea.getArtworkId());
            stmt.executeUpdate();
            System.out.println("Obra vinculada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao vincular obra: " + e.getMessage());
        }
    }

    public List<ExhibitionArtwork> readAll() {
        List<ExhibitionArtwork> lista = new ArrayList<>();
        String sql = "SELECT id, id_exhibition, id_artwork FROM exhibition_artwork";

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ExhibitionArtwork ea = new ExhibitionArtwork();
                ea.setId(rs.getLong("id"));
                ea.setExhibitionId(rs.getLong("id_exhibition"));
                ea.setArtworkId(rs.getLong("id_artwork"));
                lista.add(ea);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar vinculos: " + e.getMessage());
        }
        return lista;
    }

    // Método Update (Geralmente pouco usado em tabelas de junção, mas mantido para completude)
    public void update(ExhibitionArtwork ea) {
        String sql = "UPDATE exhibition_artwork SET id_exhibition = ?, id_artwork = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, ea.getExhibitionId());
            stmt.setLong(2, ea.getArtworkId());
            stmt.setLong(3, ea.getId());
            stmt.executeUpdate();
            System.out.println("Vinculo atualizado!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar vinculo: " + e.getMessage());
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM exhibition_artwork WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            System.out.println("Obra desvinculada!");
        } catch (SQLException e) {
            System.out.println("Erro ao desvincular: " + e.getMessage());
        }
    }
}