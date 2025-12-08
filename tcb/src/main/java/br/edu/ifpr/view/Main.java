package br.edu.ifpr.view;

import br.edu.ifpr.controller.*;
import br.edu.ifpr.dao.ConnectionFactory;
import br.edu.ifpr.dao.ExhibitionArtworkDAO;

public class Main {

    // 1. Instancia Controllers
    private static UserController uC = new UserController();
    private static ArtworkController aC = new ArtworkController(ConnectionFactory.connect());
    private static ExhibitionController eC = new ExhibitionController(ConnectionFactory.connect());
    private static CategoryController cC = new CategoryController();
    private static RatingController rC = new RatingController();
    private static ExhibitionArtworkController eaC = new ExhibitionArtworkController();
    
    // DAO auxiliar para contagens no ExploreView
    private static ExhibitionArtworkDAO eaDao = new ExhibitionArtworkDAO(ConnectionFactory.connect());

    // 2. Instancia Views (Injetando os Controllers)
    private static AuthView authView = new AuthView(uC);
    private static UserView userView = new UserView(uC);
    private static ArtworkView artworkView = new ArtworkView(aC, cC, uC);
    private static CategoryView categoryView = new CategoryView(cC, aC, uC);
    private static RatingView ratingView = new RatingView(rC, aC, eC);
    private static ExhibitionView exhibitionView = new ExhibitionView(eC, eaC, aC, eaDao);
    private static Explore exploreView = new Explore(uC, aC, eC, rC, categoryView, eaC, eaDao, cC);

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("        SISTEMA DE ARTE DIGITAL          ");
        System.out.println("=========================================");

        while (!Sessao.isLogado()) {
            authView.menuInicial();
        }

        System.out.println("\nBem-vindo(a), " + Sessao.getUsuarioLogado().getUsername() + "!");
        
        boolean rodando = true;
        while (rodando) {
            System.out.println("\n--- HOME ---");
            System.out.println("1. Explorar");
            System.out.println("2. Minhas Obras");
            System.out.println("3. Minhas Exposicoes");
            System.out.println("4. Minhas Avaliacoes");
            System.out.println("5. Meu Perfil");
            System.out.println("6. Navegar por Categorias");
            System.out.println("0. Sair (Logout)");
            System.out.print(">> ");

            int op = ViewUtils.lerInteiro();

            switch (op) {
                case 1 -> exploreView.menuExplorar();
                case 2 -> artworkView.menuMinhasObras();
                case 3 -> exhibitionView.menuMinhasExposicoes();
                case 4 -> ratingView.menuMinhasAvaliacoes();
                case 5 -> userView.menuMeuPerfil();
                case 6 -> categoryView.menuNavegarCategorias();
                case 0 -> {
                    rodando = false;
                    Sessao.logout();
                    System.out.println("Logout realizado. Ate logo!");
                }
                default -> System.out.println("Opcao invalida.");
            }
        }
    }
}