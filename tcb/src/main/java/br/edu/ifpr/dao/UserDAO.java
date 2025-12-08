package br.edu.ifpr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpr.model.User;

public class UserDAO {

    public UserDAO() {
    }

    public void create(User u) {
        String sql = "INSERT INTO users (username, email, password, location, profile_photo, biography) VALUES (?, ?, ?, ?, ?, ?)";

        // Try-with-resources fechando conexão automaticamente
        // RETURN_GENERATED_KEYS: Permite recuperar o ID (Auto Increment) criado pelo banco
        try (Connection conn = ConnectionFactory.connect();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getPassword());
            stmt.setString(4, u.getLocation());
            stmt.setString(5, u.getProfilePhoto());
            stmt.setString(6, u.getBiography());

            stmt.executeUpdate();

            // Recupera o ID gerado para atualizar o objeto Java
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                u.setId(keys.getInt(1));
            }
            keys.close();
            System.out.println("Usuário criado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao criar usuário: " + e.getMessage());
        }
    }

    public List<User> readAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = ConnectionFactory.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setLocation(rs.getString("location"));
                u.setProfilePhoto(rs.getString("profile_photo"));
                u.setBiography(rs.getString("biography"));
                users.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    public User findById(long id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = ConnectionFactory.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setLocation(rs.getString("location"));
                u.setProfilePhoto(rs.getString("profile_photo"));
                u.setBiography(rs.getString("biography"));
                return u;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário por id: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Busca usuário por email (Usado no Login)
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = ConnectionFactory.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setLocation(rs.getString("location"));
                u.setProfilePhoto(rs.getString("profile_photo"));
                u.setBiography(rs.getString("biography"));
                return u;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário por email: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void update(User u) {
        String sql = "UPDATE users SET username = ?, email = ?, password = ?, location = ?, profile_photo = ?, biography = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getPassword());
            stmt.setString(4, u.getLocation());
            stmt.setString(5, u.getProfilePhoto());
            stmt.setString(6, u.getBiography());
            stmt.setLong(7, u.getId());

            stmt.executeUpdate();
            System.out.println("Usuário atualizado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = ConnectionFactory.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            System.out.println("Usuário removido.");

        } catch (SQLException e) {
            System.out.println("Erro ao deletar usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }
}