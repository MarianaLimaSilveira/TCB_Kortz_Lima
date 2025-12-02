package br.edu.ifpr.view;

import java.util.Scanner;

import br.edu.ifpr.controller.ArtworkController;
import br.edu.ifpr.controller.CategoryController;
import br.edu.ifpr.controller.ExhibitionArtworkController;
import br.edu.ifpr.controller.ExhibitionController;
import br.edu.ifpr.controller.RatingController;
import br.edu.ifpr.controller.Sessao;
import br.edu.ifpr.controller.UserController;
import br.edu.ifpr.dao.ConnectionFactory;
import br.edu.ifpr.model.Artwork;
import br.edu.ifpr.model.Category;
import br.edu.ifpr.model.Exhibition;
import br.edu.ifpr.model.Rating;
import br.edu.ifpr.model.User;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        UserController userController = new UserController();
        RatingController ratingController = new RatingController();

        ArtworkController artworkController = new ArtworkController(ConnectionFactory.connect());
        ExhibitionController exhibitionController = new ExhibitionController(ConnectionFactory.connect());
        ExhibitionArtworkController eaController = new ExhibitionArtworkController();

        CategoryController categoryController = new CategoryController();

        System.out.println("=== Sistema de Arte Digital ===");

        boolean logado = false;
        while (!logado) {
            System.out.println("\n1 - login");
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Senha: ");
            String senha = sc.nextLine();

            logado = userController.login(email, senha);

            if (!logado) {
                System.out.println("Login inválido. Tente novamente.");
            }
        }

        System.out.println("\nBem-vindo, " + Sessao.getUsuarioLogado().getUsername() + "!");

    
        int op = -1;

        while (op != 0) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Usuários");
            System.out.println("2 - Obras");
            System.out.println("3 - Exposições");
            System.out.println("4 - Categorias");
            System.out.println("5 - Avaliações");
            System.out.println("6 - Vínculo Exposição↔Obra");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            
            try { op = Integer.parseInt(sc.nextLine()); }
            catch (Exception e) { op = -1; }

            switch(op) {

                case 1 -> menuUsuarios(userController);
                case 2 -> menuObras(artworkController);
                case 3 -> menuExposicoes(exhibitionController);
                case 4 -> menuCategorias(categoryController);
                case 5 -> menuAvaliacoes(ratingController);
                case 6 -> eaController.menuExhibitionArtwork();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }
        }

    }


    private static void menuUsuarios(UserController userC) {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- Usuários ---");
            System.out.println("1 - Listar");
            System.out.println("2 - Criar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Deletar");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            try { op = Integer.parseInt(sc.nextLine()); }
            catch (Exception e) { op = -1; }

            switch (op) {
                case 1 -> userC.listUsers().forEach(u -> 
                    System.out.println(u.getId() + " | " + u.getUsername() + " | " + u.getEmail()));
                
                case 2 -> {
                    User u = new User();
                    System.out.print("Username: ");
                    u.setUsername(sc.nextLine());
                    System.out.print("Email: ");
                    u.setEmail(sc.nextLine());
                    System.out.print("Senha: ");
                    u.setPassword(sc.nextLine());
                    userC.createUser(u);
                }

                case 3 -> {
                    System.out.print("ID do usuário: ");
                    int id = Integer.parseInt(sc.nextLine());
                    User u = userC.listUsers().stream()
                            .filter(x -> x.getId() == id).findFirst().orElse(null);
                    if (u == null) { System.out.println("Não encontrado."); break; }
                    System.out.print("Novo username: ");
                    u.setUsername(sc.nextLine());
                    userC.updateUser(u);
                }

                case 4 -> {
                    System.out.print("ID: ");
                    int id = Integer.parseInt(sc.nextLine());
                    userC.deleteUser(id);
                }

                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        }
    }


    private static void menuObras(ArtworkController artC) {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- Obras ---");
            System.out.println("1 - Listar");
            System.out.println("2 - Criar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Deletar");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            try { op = Integer.parseInt(sc.nextLine()); } catch (Exception e) {}

            switch (op) {
                case 1 -> artC.listArtworks().forEach(a ->
                    System.out.println(a.getId() + " | " + a.getTitle() + " | Categoria: " + a.getIdCategory()));

                case 2 -> {
                    Artwork a = new Artwork();
                    System.out.print("Título: ");
                    a.setTitle(sc.nextLine());
                    System.out.print("Descrição: ");
                    a.setDescription(sc.nextLine());
                    System.out.print("Categoria (string): ");
                    a.setIdCategory(sc.nextLong());
                    a.setIdUser((int) Sessao.getUsuarioLogado().getId());
                    artC.createArtwork(a);
                }

                case 3 -> {
                    System.out.print("ID da obra: ");
                    int id = Integer.parseInt(sc.nextLine());
                    Artwork a = artC.listArtworks().stream()
                            .filter(x -> x.getId() == id).findFirst().orElse(null);
                    if (a == null) { System.out.println("Not found"); break; }
                    System.out.print("Novo título: ");
                    a.setTitle(sc.nextLine());
                    artC.updateArtwork(a);
                }

                case 4 -> {
                    System.out.print("ID da obra: ");
                    int id = Integer.parseInt(sc.nextLine());
                    artC.deleteArtwork(id);
                }

                case 0 -> {}
            }
        }
    }


    private static void menuExposicoes(ExhibitionController exC) {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- Exposições ---");
            System.out.println("1 - Listar");
            System.out.println("2 - Criar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Deletar");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            try { op = Integer.parseInt(sc.nextLine()); } catch (Exception e) {}

            switch (op) {
                case 1 -> exC.listExhibitions().forEach(e ->
                    System.out.println(e.getId() + " | " + e.getName() + " | " + e.getTheme()));

                case 2 -> {
                    Exhibition ex = new Exhibition();
                    ex.setIdCreator(Sessao.getUsuarioLogado().getId());

                    System.out.print("Nome: ");
                    ex.setName(sc.nextLine());
                    System.out.print("Tema: ");
                    ex.setTheme(sc.nextLine());
                    System.out.print("Descrição: ");
                    ex.setDescription(sc.nextLine());
                    System.out.print("Data início: ");
                    ex.setStartDate(sc.nextLine());
                    System.out.print("Data fim: ");
                    ex.setEndDate(sc.nextLine());

                    exC.createExhibition(ex);
                }

                case 3 -> System.out.println("Implementação simples: atualize direto no DB se precisar.");

                case 4 -> {
                    System.out.print("ID da exposição: ");
                    int id = Integer.parseInt(sc.nextLine());
                    exC.deleteExhibition(id);
                }

                case 0 -> {}
            }
        }
    }


    private static void menuCategorias(CategoryController catC) {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- Categorias ---");
            System.out.println("1 - Listar");
            System.out.println("2 - Criar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Deletar");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            try { op = Integer.parseInt(sc.nextLine()); } catch (Exception e) {}

            switch (op) {
                case 1 -> catC.listCategories().forEach(c ->
                    System.out.println(c.getId() + " | " + c.getName()));

                case 2 -> {
                    Category c = new Category();
                    System.out.print("Nome: ");
                    c.setName(sc.nextLine());
                    System.out.print("Descrição: ");
                    c.setDescription(sc.nextLine());
                    catC.createCategory(c);
                }

                case 3 -> System.out.println("Atualização simplificada, altere no DB se necessário.");

                case 4 -> {
                    System.out.print("ID da categoria: ");
                    long id = Long.parseLong(sc.nextLine());
                    catC.deleteCategory(id);
                }

                case 0 -> {}
            }
        }
    }


    private static void menuAvaliacoes(RatingController rC) {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- Avaliações ---");
            System.out.println("1 - Listar");
            System.out.println("2 - Criar");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            try { op = Integer.parseInt(sc.nextLine()); } catch (Exception e) {}

            switch (op) {
                case 1 -> rC.listRatings().forEach(r ->
                    System.out.println(r.getId() + " | Nota: " + r.getNote() + " | Texto: " + r.getText())
                );

                case 2 -> {
                    Rating r = new Rating();
                    r.setUserId(Sessao.getUsuarioLogado().getId());

                    System.out.print("Avaliar obra (id) ou ENTER para pular: ");
                    String obraStr = sc.nextLine();
                    if (!obraStr.isEmpty()) r.setArtworkId(Long.parseLong(obraStr));

                    System.out.print("Avaliar exposição (id) ou ENTER para pular: ");
                    String expStr = sc.nextLine();
                    if (!expStr.isEmpty()) r.setExhibitionId(Long.parseLong(expStr));

                    System.out.print("Nota (1-5): ");
                    r.setNote(Integer.parseInt(sc.nextLine()));

                    System.out.print("Comentário: ");
                    r.setText(sc.nextLine());

                    rC.createRating(r);
                }

                case 0 -> {}
            }
        }
    }

}
