package br.edu.ifpr.view;
import br.edu.ifpr.controller.*;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import br.edu.ifpr.dao.ConnectionFactory;
import br.edu.ifpr.model.*;

public class Main {

    private static final Scanner input = new Scanner(System.in);

    private  static  final ConnectionFactory database = new ConnectionFactory();

    private static final UserController userController = new UserController();
    private static final ArtworkController artworkController = new ArtworkController(database.connect());
    private static final CategoryController categoryController = new CategoryController();
    private static final ExhibitionController exhibitionController = new ExhibitionController(database.connect());
    private static final ExhibitionArtworkController exhibitionArtworkController = new ExhibitionArtworkController();
    private static final RatingController ratingController = new RatingController();

    public static void main(String[] args) {

        int opcao;

        do {
            System.out.println("\n SISTEMA DE EXPOSIÇÃO DE ARTE DIGITAL");
            System.out.println("1 - Usuários");
            System.out.println("2 - Obras Digitais");
            System.out.println("3 - Categorias");
            System.out.println("4 - Exposições");
            System.out.println("5 - Avaliações");
            System.out.println("6 - Vínculo Exposição ↔ Obra");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = Integer.parseInt(input.nextLine());

            switch (opcao) {
                case 1 -> menuUsuarios();
                case 2 -> menuObras();
                case 3 -> menuCategorias();
                case 4 -> menuExposicoes();
                case 5 -> menuAvaliacoes();
                case 6 -> exhibitionArtworkController.menuExhibitionArtwork();
                case 0 -> System.out.println("Encerrando sistema...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private static void menuUsuarios() {
        int op;
        do {
            System.out.println("\n MENU USUÁRIOS");
            System.out.println("1 - Criar usuário");
            System.out.println("2 - Listar usuários");
            System.out.println("3 - Atualizar usuário");
            System.out.println("4 - Excluir usuário");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            op = Integer.parseInt(input.nextLine());

            switch (op) {
                case 1 -> criarUsuario();
                case 2 -> listarUsuarios();
                case 3 -> atualizarUsuario();
                case 4 -> excluirUsuario();
            }
        } while (op != 0);
    }

    private static void criarUsuario() {
        User u = new User();

        System.out.print("Username: ");
        u.setUsername(input.nextLine());

        System.out.print("Email: ");
        u.setEmail(input.nextLine());

        System.out.print("Senha: ");
        u.setPassword(input.nextLine());

        System.out.print("Localização: ");
        u.setLocation(input.nextLine());

        System.out.print("Quer escolher uma foto de perfil? (s/n): ");
        String resp = input.nextLine();

        if(resp.equalsIgnoreCase("s")) {
            String caminho = FilePicker.selecionarFotoPerfil();
            if(caminho != null) {
                u.setProfilePhoto(caminho);
                System.out.println("Foto selecionada: " + caminho);
            } else {
                System.out.println("Nenhuma foto selecionada.");
            }
        } else {
            u.setProfilePhoto(null); // ou ""
        }


        System.out.print("Biografia: ");
        u.setBiography(input.nextLine());

        userController.createUser(u);
    }

    private static void listarUsuarios() {
        List<User> lista = userController.listUsers();
        System.out.println("\n--- LISTA DE USUÁRIOS ---");
        lista.forEach(System.out::println);
    }

    private static void atualizarUsuario() {
        User u = new User();

        System.out.print("ID do usuário: ");
        u.setId(Integer.parseInt(input.nextLine()));

        System.out.print("Novo username: ");
        u.setUsername(input.nextLine());

        System.out.print("Novo email: ");
        u.setEmail(input.nextLine());

        System.out.print("Nova senha: ");
        u.setPassword(input.nextLine());

        System.out.print("Nova localização: ");
        u.setLocation(input.nextLine());

        System.out.print("Nova foto de perfil: ");
        u.setProfilePhoto(input.nextLine());

        System.out.print("Nova biografia: ");
        u.setBiography(input.nextLine());

        userController.updateUser(u);
    }

    private static void excluirUsuario() {
        System.out.print("ID para excluir: ");
        int id = Integer.parseInt(input.nextLine());
        userController.deleteUser(id);
    }

    private static void menuObras() {
        int op;
        do {
            System.out.println("\nMENU OBRAS");
            System.out.println("1 - Criar obra");
            System.out.println("2 - Listar obras");
            System.out.println("3 - Atualizar obra");
            System.out.println("4 - Excluir obra");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            op = Integer.parseInt(input.nextLine());

            switch (op) {
                case 1 -> criarObra();
                case 2 -> listarObras();
                case 3 -> atualizarObra();
                case 4 -> excluirObra();
            }
        } while (op != 0);
    }

    private static void criarObra() {
        Artwork a = new Artwork();

        System.out.print("Título: ");
        a.setTitle(input.nextLine());

        System.out.print("Descrição: ");
        a.setDescription(input.nextLine());

        System.out.print("Categoria (texto): ");
        a.setIdCategory(input.nextLine());

        System.out.print("ID do artista/criador: ");
        a.setIdUser(Integer.parseInt(input.nextLine()));

        artworkController.createArtwork(a);
    }

    private static void listarObras() {
        artworkController.listArtworks().forEach(System.out::println);
    }

    private static void atualizarObra() {
        Artwork a = new Artwork();

        System.out.print("ID da obra: ");
        a.setId(Integer.parseInt(input.nextLine()));

        System.out.print("Novo título: ");
        a.setTitle(input.nextLine());

        System.out.print("Nova descrição: ");
        a.setDescription(input.nextLine());

        System.out.print("Nova categoria: ");
        a.setIdCategory(input.nextLine());

        System.out.print("Novo ID de artista: ");
        a.setIdUser(Integer.parseInt(input.nextLine()));

        artworkController.updateArtwork(a);
    }

    private static void excluirObra() {
        System.out.print("ID da obra: ");
        int id = Integer.parseInt(input.nextLine());
        artworkController.deleteArtwork(id);
    }

    private static void menuCategorias() {
        int op;
        do {
            System.out.println("\n🏷️ MENU CATEGORIAS");
            System.out.println("1 - Criar categoria");
            System.out.println("2 - Listar categorias");
            System.out.println("3 - Atualizar categoria");
            System.out.println("4 - Excluir categoria");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            op = Integer.parseInt(input.nextLine());

            switch (op) {
                case 1 -> criarCategoria();
                case 2 -> listarCategorias();
                case 3 -> atualizarCategoria();
                case 4 -> excluirCategoria();
            }
        } while (op != 0);
    }

    private static void criarCategoria() {
        Category c = new Category();

        System.out.print("Nome: ");
        c.setName(input.nextLine());

        System.out.print("Descrição: ");
        c.setDescription(input.nextLine());

        categoryController.createCategory(c);
    }

    private static void listarCategorias() {
        categoryController.listCategories().forEach(System.out::println);
    }

    private static void atualizarCategoria() {
        Category c = new Category();

        System.out.print("ID da categoria: ");
        c.setId(Long.parseLong(input.nextLine()));

        System.out.print("Novo nome: ");
        c.setName(input.nextLine());

        System.out.print("Nova descrição: ");
        c.setDescription(input.nextLine());

        categoryController.updateCategory(c);
    }

    private static void excluirCategoria() {
        System.out.print("ID da categoria: ");
        long id = Long.parseLong(input.nextLine());
        categoryController.deleteCategory(id);
    }

    private static void menuExposicoes() {
        int op;
        do {
            System.out.println("\n🏛 MENU EXPOSIÇÕES");
            System.out.println("1 - Criar exposição");
            System.out.println("2 - Listar exposições");
            System.out.println("3 - Atualizar exposição");
            System.out.println("4 - Excluir exposição");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            op = Integer.parseInt(input.nextLine());

            switch (op) {
                case 1 -> criarExposicao();
                case 2 -> listarExposicoes();
                case 3 -> atualizarExposicao();
                case 4 -> excluirExposicao();
            }
        } while (op != 0);
    }

    private static void criarExposicao() {
        Exhibition ex = new Exhibition();

        System.out.print("ID do criador: ");
        ex.setIdCreator(Integer.parseInt(input.nextLine()));

        System.out.print("Nome: ");
        ex.setName(input.nextLine());

        System.out.print("Tema: ");
        ex.setTheme(input.nextLine());

        System.out.print("Data início (AAAA-MM-DD): ");
        ex.setStartDate(input.nextLine());

        System.out.print("Data fim (AAAA-MM-DD): ");
        ex.setEndDate(input.nextLine());

        exhibitionController.createExhibition(ex);
    }

    private static void listarExposicoes() {
        exhibitionController.listExhibitions().forEach(System.out::println);
    }

    private static void atualizarExposicao() {
        Exhibition ex = new Exhibition();

        System.out.print("ID da exposição: ");
        ex.setId(Integer.parseInt(input.nextLine()));

        System.out.print("Novo criador: ");
        ex.setIdCreator(Integer.parseInt(input.nextLine()));

        System.out.print("Novo nome: ");
        ex.setName(input.nextLine());

        System.out.print("Novo tema: ");
        ex.setTheme(input.nextLine());

        System.out.print("Nova data início: ");
        ex.setStartDate(input.nextLine());

        System.out.print("Nova data fim: ");
        ex.setEndDate(input.nextLine());

        exhibitionController.updateExhibition(ex);
    }

    private static void excluirExposicao() {
        System.out.print("ID da exposição: ");
        int id = Integer.parseInt(input.nextLine());
        exhibitionController.deleteExhibition(id);
    }

    private static void menuAvaliacoes() {
        int op;
        do {
            System.out.println("\n⭐ MENU AVALIAÇÕES");
            System.out.println("1 - Criar avaliação");
            System.out.println("2 - Listar avaliações");
            System.out.println("3 - Atualizar avaliação");
            System.out.println("4 - Excluir avaliação");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            op = Integer.parseInt(input.nextLine());

            switch (op) {
                case 1 -> criarRating();
                case 2 -> listarRatings();
                case 3 -> atualizarRating();
                case 4 -> excluirRating();
            }
        } while (op != 0);
    }

    private static void criarRating() {
        Rating r = new Rating();

        System.out.print("ID do usuário: ");
        r.setUserId(Long.parseLong(input.nextLine()));

        System.out.print("ID da obra (ou ENTER para ignorar): ");
        String obra = input.nextLine();
        r.setArtworkId(obra.isEmpty() ? null : Long.parseLong(obra));

        System.out.print("ID da exposição (ou ENTER para ignorar): ");
        String exp = input.nextLine();
        r.setExhibitionId(exp.isEmpty() ? null : Long.parseLong(exp));

        System.out.print("Nota (1-5): ");
        r.setNote(Integer.parseInt(input.nextLine()));

        System.out.print("Comentário: ");
        r.setText(input.nextLine());

        ratingController.createRating(r);
    }

    private static void listarRatings() {
        ratingController.listRatings().forEach(System.out::println);
    }

    private static void atualizarRating() {
        Rating r = new Rating();

        System.out.print("ID da avaliação: ");
        r.setId(Long.parseLong(input.nextLine()));

        System.out.print("Nova nota: ");
        r.setNote(Integer.parseInt(input.nextLine()));

        System.out.print("Novo comentário: ");
        r.setText(input.nextLine());

        ratingController.updateRating(r);
    }

    private static void excluirRating() {
        System.out.print("ID da avaliação: ");
        long id = Long.parseLong(input.nextLine());
        ratingController.deleteRating(id);
    }
}
