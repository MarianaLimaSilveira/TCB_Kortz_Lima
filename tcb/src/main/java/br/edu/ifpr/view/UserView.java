package br.edu.ifpr.view;

import br.edu.ifpr.controller.Sessao;
import br.edu.ifpr.controller.UserController;
import br.edu.ifpr.model.User;

public class UserView {

    private UserController userController;

    public UserView(UserController userController) {
        this.userController = userController;
    }

    public void menuMeuPerfil() {
        User u = Sessao.getUsuarioLogado();
        System.out.println("\n=== MEU PERFIL ===");
        System.out.println("1. Nome: " + u.getUsername());
        System.out.println("2. Bio: " + u.getBiography());
        System.out.println("3. Localizacao: " + u.getLocation());
        System.out.println("4. Foto URL: " + u.getProfilePhoto());
        System.out.println("5. Senha: *****");
        System.out.println("---------------------");
        System.out.println("6. DELETAR CONTA");
        System.out.println("0. Voltar");
        System.out.print("Digite o numero do campo que deseja alterar (ou 0 para sair): ");
        
        int op = ViewUtils.lerInteiro();
        
        if (op >= 1 && op <= 5) {
            System.out.print("Novo valor: ");
            String novoValor = ViewUtils.sc.nextLine();
            
            if (!novoValor.isEmpty()) {
                switch(op) {
                    case 1 -> u.setUsername(novoValor);
                    case 2 -> u.setBiography(novoValor);
                    case 3 -> u.setLocation(novoValor);
                    case 4 -> u.setProfilePhoto(novoValor);
                    case 5 -> u.setPassword(novoValor);
                }
                userController.updateUser(u);
                Sessao.setUsuarioLogado(u);
                System.out.println("Atualizado com sucesso!");
            }
        } else if (op == 6) {
            System.out.print("Digite a senha para confirmar exclusao: ");
            String confirmSenha = ViewUtils.sc.nextLine();
            if (confirmSenha.equals(u.getPassword())) {
                userController.deleteUser((int)u.getId());
                Sessao.logout();
                System.out.println("Conta deletada.");
                System.exit(0);
            } else {
                System.out.println("Senha incorreta.");
            }
        }
    }
}