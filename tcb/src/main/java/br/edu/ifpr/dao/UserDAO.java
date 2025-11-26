package br.edu.ifpr.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpr.model.User;

public class UserDAO {

    public void create(User user) {
        String sql = "INSERT INTO users " +
                "(username, email, password, location, profile_photo, biography) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getLocation());
            stmt.setString(5, user.getProfilePhoto());
            stmt.setString(6, user.getBiography());

            stmt.executeUpdate();
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
                u.setProfilePhoto(rs.getString("profilePhoto"));
                u.setBiography(rs.getString("biography"));

                users.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao ler usuários: " + e.getMessage());
        }

        return users;
    }

    public void update(User user) {
        String sql = "UPDATE users " +
                "SET username=?, email=?, password=?, location=?, profilePhoto=?, biography=? " +
                "WHERE id=?";

        try (Connection conn = ConnectionFactory.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getLocation());
            stmt.setString(5, user.getProfilePhoto());
            stmt.setString(6, user.getBiography());
            stmt.setInt(7, user.getId());

            stmt.executeUpdate();
            System.out.println("Usuário atualizado!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id=?";

        try (Connection conn = ConnectionFactory.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Usuário deletado!");

        } catch (SQLException e) {
            System.out.println("Erro ao deletar usuário: " + e.getMessage());
        }
    }
}
