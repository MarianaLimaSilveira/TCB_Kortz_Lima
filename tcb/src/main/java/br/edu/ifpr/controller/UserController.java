package br.edu.ifpr.controller;

import java.util.List;

import br.edu.ifpr.dao.RatingDAO;
import br.edu.ifpr.dao.UserDAO;
import br.edu.ifpr.model.Rating;
import br.edu.ifpr.model.User;
// imports...

public class UserController {

    private static UserDAO userDAO = new UserDAO();
    private RatingDAO ratingDAO = new RatingDAO();

    public void createUser(User user) {
        // Validação básica de campos obrigatórios
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
        // Regra de Negócio: Não excluímos usuários ativos com histórico (avaliações).
        // Em um sistema real, faríamos apenas "soft delete" (desativar).
        if (usuarioTemRatings(id_user)) {
            System.out.println("O usuário possui avaliações e não pode ser excluído (apenas suspenso).");
            return;
        }
        userDAO.delete(id_user);
    }

    // Método auxiliar para verificar integridade antes de deletar
    private boolean usuarioTemRatings(int id_user) {
        List<Rating> ratings = ratingDAO.readAll();
        if (ratings == null)
            return false;
        return ratings.stream().anyMatch(r -> r.getUserId() == id_user);
    }

    public boolean login(String email, String senha) {
        // Busca usuário pelo email para autenticação
        User u = userDAO.findByEmail(email);

        if (u == null) {
            return false; // Usuário não existe
        }

        // Comparação simples de senha (em produção usaria Hash/BCrypt)
        if (!u.getPassword().equals(senha)) {
            return false; // Senha incorreta
        }

        // Sucesso: Salva o usuário na sessão global
        Sessao.setUsuarioLogado(u);

        return true;
    }
}