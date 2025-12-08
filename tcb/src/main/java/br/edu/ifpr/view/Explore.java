package br.edu.ifpr.view;

import java.util.List;
import br.edu.ifpr.controller.*;
import br.edu.ifpr.dao.ExhibitionArtworkDAO;
import br.edu.ifpr.model.*;

public class Explore {

    private UserController userController;
    private ArtworkController artworkController;
    private ExhibitionController exhibitionController;
    private RatingController ratingController;
    private CategoryView categoryView;
    private ExhibitionArtworkController eaController;
    private ExhibitionArtworkDAO eaDaoLeitura;
    private CategoryController categoryController;

    public Explore(UserController uC, ArtworkController aC, ExhibitionController eC, 
                   RatingController rC, CategoryView cV, ExhibitionArtworkController eaC, 
                   ExhibitionArtworkDAO dao, CategoryController cC) {
        this.userController = uC;
        this.artworkController = aC;
        this.exhibitionController = eC;
        this.ratingController = rC;
        this.categoryView = cV;
        this.eaController = eaC;
        this.eaDaoLeitura = dao;
        this.categoryController = cC;
    }

    public void menuExplorar() {
        System.out.println("\n=== EXPLORAR ===");
        System.out.println("1. Listar Obras");
        System.out.println("2. Listar Artistas (com estatisticas)");
        System.out.println("3. Listar Exposicoes");
        System.out.println("4. Navegar por Categorias");
        System.out.println("---------------------------");
        System.out.println("5. Ver Detalhes de um Artista");
        System.out.println("6. Ver Obras de uma Exposicao");
        System.out.println("0. Voltar");
        System.out.print(">> ");

        int op = ViewUtils.lerInteiro();
        switch (op) {
            case 1 -> {
                System.out.println("\n=== Feed de Obras ===");
                List<Artwork> todas = artworkController.listArtworks();
                if(todas.isEmpty()) System.out.println("Nenhuma obra no sistema.");
                else todas.forEach(a -> ViewUtils.mostrarObraDetalhada(a, userController, categoryController));
            }
            case 2 -> {
                System.out.println("\n=== Comunidade ===");
                List<User> users = userController.listUsers();
                List<Artwork> arts = artworkController.listArtworks();
                List<Exhibition> expos = exhibitionController.listExhibitions();
                List<Rating> ratings = ratingController.listRatings();

                for (User u : users) {
                    long qtdObras = arts.stream().filter(a -> a.getIdUser() == u.getId()).count();
                    long qtdExpos = expos.stream().filter(e -> e.getIdCreator() == u.getId()).count();
                    long qtdReviews = ratings.stream().filter(r -> r.getUserId() == u.getId()).count();

                    System.out.println("ID: " + u.getId() + " | " + u.getUsername() + 
                                       " | Obras: " + qtdObras + ", Expos: " + qtdExpos + ", Reviews: " + qtdReviews);
                }
            }
            case 3 -> {
                System.out.println("\n=== Exposicoes ===");
                List<Exhibition> expos = exhibitionController.listExhibitions();
                List<ExhibitionArtwork> vinculos = eaDaoLeitura.readAll(); 

                for (Exhibition e : expos) {
                    long qtdObras = vinculos.stream().filter(v -> v.getExhibitionId() == e.getId()).count();
                    System.out.println("ID: " + e.getId() + " | " + e.getName() + 
                                       " (" + e.getTheme() + ") | [" + qtdObras + " obras]");
                }
            }
            case 4 -> categoryView.menuNavegarCategorias();
            
            case 5 -> {
                System.out.println("\n--- Lista de Artistas ---");
                userController.listUsers().forEach(u -> System.out.println("[" + u.getId() + "] " + u.getUsername()));
                
                System.out.print("Digite o ID do Usuario/Artista: ");
                long userId = Long.parseLong(ViewUtils.sc.nextLine());
                User u = userController.listUsers().stream().filter(x -> x.getId() == userId).findFirst().orElse(null);
                
                if(u != null) {
                    System.out.println("\n=================================");
                    System.out.println("   PERFIL DE " + u.getUsername().toUpperCase());
                    System.out.println("=================================");
                    System.out.println("Foto: " + u.getProfilePhoto());
                    System.out.println("Local: " + u.getLocation());
                    System.out.println("Bio: " + u.getBiography());
                    
                    System.out.println("\n--- Obras Publicadas ---");
                    List<Artwork> obras = artworkController.listArtworks().stream()
                        .filter(a -> a.getIdUser() == userId).toList();
                    if(obras.isEmpty()) System.out.println("Nenhuma obra postada.");
                    else obras.forEach(a -> System.out.println("- " + a.getTitle()));

                    System.out.println("\n--- Exposicoes Criadas ---");
                    List<Exhibition> expos = exhibitionController.listExhibitions().stream()
                        .filter(e -> e.getIdCreator() == userId).toList();
                    if(expos.isEmpty()) System.out.println("Nenhuma exposicao criada.");
                    else expos.forEach(e -> System.out.println("- " + e.getName()));

                    System.out.println("\n--- Avaliacoes Feitas pelo Usuario ---");
                    List<Rating> reviews = ratingController.listRatings().stream()
                        .filter(r -> r.getUserId() == userId).toList();
                    
                    if(reviews.isEmpty()) System.out.println("Nenhuma avaliacao feita.");
                    else {
                        for(Rating r : reviews) {
                            String itemAvaliado = "Desconhecido";
                            if(r.getArtworkId() != null && r.getArtworkId() > 0) itemAvaliado = "Obra ID " + r.getArtworkId();
                            else if(r.getExhibitionId() != null && r.getExhibitionId() > 0) itemAvaliado = "Exposicao ID " + r.getExhibitionId();
                            
                            System.out.println("Avaliou " + itemAvaliado + ": Nota " + r.getNote() + " - \"" + r.getText() + "\"");
                        }
                    }

                } else {
                    System.out.println("Usuario nao encontrado.");
                }
            }
            case 6 -> {
                System.out.println("\n--- Lista de Exposicoes ---");
                exhibitionController.listExhibitions().forEach(e -> System.out.println("[" + e.getId() + "] " + e.getName()));
                
                System.out.print("Digite o ID da Exposicao: ");
                long exId = Long.parseLong(ViewUtils.sc.nextLine());
                eaController.listarObrasDeExposicao(exId);
            }
            case 0 -> {}
        }
    }
}