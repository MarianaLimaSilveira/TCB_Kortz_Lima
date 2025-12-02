package br.edu.ifpr.controller;

import java.util.List;

import br.edu.ifpr.dao.RatingDAO;
import br.edu.ifpr.dao.UserDAO;
import br.edu.ifpr.model.Rating;
import br.edu.ifpr.model.User;

public class UserController {

    private static UserDAO userDAO = new UserDAO();
    private RatingDAO ratingDAO = new RatingDAO();

    public void createUser(User user) {
        if (user.getUsername().isEmpty() || user.getEmail().isEmpty()) {
            System.out.println("Campos obrigatórios não podem estar vazios!");
            return;
        }
        userDAO.create(user);
    }

    public List<User> listUsers() {
        return userDAO.readAll();
    }

    public void updateUser(User user) {
        userDAO.update(user);
    }

    public void deleteUser(int id_user) {
        if (usuarioTemRatings(id_user)) {
            System.out.println("O usuário possui avaliações e não pode ser excluído (apenas suspenso).");
            return;
        }
        userDAO.delete(id_user);
    }

    private boolean usuarioTemRatings(int id_user) {
        List<Rating> ratings = ratingDAO.readAll();
        if (ratings == null)
            return false;
        return ratings.stream().anyMatch(r -> r.getUserId() == id_user);
    }

    public boolean login(String email, String senha) {
        User u = userDAO.findByEmail(email);

        if (u == null) {
            return false;
        }

        if (!u.getPassword().equals(senha)) {
            return false;
        }

        Sessao.setUsuarioLogado(u);

        return true;
    }
}
