package br.edu.ifpr.view;

import br.edu.ifpr.controller.Sessao;
import br.edu.ifpr.controller.UserController;
import br.edu.ifpr.model.User;

public class AuthView {

    private UserController userController;

    public AuthView(UserController userController) {
        this.userController = userController;
    }

    public void menuInicial() {
        System.out.println("\n1. Entrar com conta existente");
        System.out.println("2. Criar nova conta");
        System.out.println("0. Sair do sistema");
        System.out.print(">> ");
        
        int op = ViewUtils.lerInteiro();

        switch (op) {
            case 1 -> {
                System.out.print("Email: ");
                String email = ViewUtils.sc.nextLine();
                System.out.print("Senha: ");
                String senha = ViewUtils.sc.nextLine();
                boolean sucesso = userController.login(email, senha);
                if (!sucesso) System.out.println("Login invalido. Tente novamente");
            }
            case 2 -> {
                User u = new User();
                System.out.println("\n=== Novo Cadastro ===");
                System.out.print("Nome de usuario: ");
                u.setUsername(ViewUtils.sc.nextLine());
                System.out.print("Email: ");
                u.setEmail(ViewUtils.sc.nextLine());
                System.out.print("Senha: ");
                u.setPassword(ViewUtils.sc.nextLine());
                
                u.setLocation("Nao informado");
                u.setBiography("Sem biografia");
                u.setProfilePhoto("default.png");
                
                userController.createUser(u);
                
                if (u.getId() > 0) {
                    Sessao.setUsuarioLogado(u);
                    System.out.println("Conta criada! Voce ja esta logado.");
                } else {
                    System.out.println("Conta criada! Faca login para continuar.");
                }
            }
            case 0 -> {
                System.out.println("Encerrando...");
                System.exit(0);
            }
            default -> System.out.println("Opcao invalida.");
        }
    }
}