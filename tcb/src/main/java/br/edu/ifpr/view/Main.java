package br.edu.ifpr.view;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.edu.ifpr.dao.ArtworkDAO;
import br.edu.ifpr.dao.CategoryDAO;
import br.edu.ifpr.dao.ConnectionFactory;
import br.edu.ifpr.dao.ExhibitionArtworkDAO;
import br.edu.ifpr.dao.ExhibitionDAO;
import br.edu.ifpr.dao.RatingDAO;
import br.edu.ifpr.dao.UserDAO;
import br.edu.ifpr.model.Artwork;
import br.edu.ifpr.model.Category;
import br.edu.ifpr.model.Exhibition;
import br.edu.ifpr.model.ExhibitionArtwork;
import br.edu.ifpr.model.Rating;
import br.edu.ifpr.model.User;

public class Main {

    // Scanner global e Usuário Logado (Sessão)
    static Scanner sc = new Scanner(System.in);
    static User loggedUser = null;

    // Instanciação dos DAOs
    static UserDAO userDAO = new UserDAO();
    static ArtworkDAO artworkDAO = new ArtworkDAO();
    static CategoryDAO categoryDAO = new CategoryDAO();
    static ExhibitionDAO exhibitionDAO = new ExhibitionDAO();
    static RatingDAO ratingDAO = new RatingDAO();
    static ExhibitionArtworkDAO eaDAO = new ExhibitionArtworkDAO(ConnectionFactory.connect());

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("      ART.NET - A REDE SOCIAL DE ARTE    ");
        System.out.println("=========================================");

        boolean rodando = true;

        while (rodando) {
            if (loggedUser == null) {
                menuInicial();
            } else {
                menuPrincipal();
            }
        }
    }

    // --- 1. FLUXO DE ENTRADA (LOGIN/CADASTRO) ---

    private static void menuInicial() {
        System.out.println("\n--- BEM-VINDO ---");
        System.out.println("1 - Login");
        System.out.println("2 - Cadastrar Nova Conta");
        System.out.println("0 - Sair");
        System.out.print("Opção: ");

        int op = lerInt();

        switch (op) {
            case 1 -> login();
            case 2 -> cadastrarUsuario();
            case 0 -> System.exit(0);
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void login() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        User u = userDAO.findByEmail(email);

        if (u != null && u.getPassword().equals(senha)) {
            loggedUser = u;
            System.out.println("Login realizado com sucesso!");
        } else {
            System.out.println("Email ou senha incorretos.");
        }
    }

    private static void cadastrarUsuario() {
        System.out.println("\n--- CRIAR CONTA ---");
        User u = new User();
        System.out.print("Nome de usuário (@username): ");
        u.setUsername(sc.nextLine());
        System.out.print("Email: ");
        u.setEmail(sc.nextLine());
        System.out.print("Senha: ");
        u.setPassword(sc.nextLine());
        System.out.print("Localização (Cidade/País): ");
        u.setLocation(sc.nextLine());
        
        // O DAO já popula o ID no objeto 'u' após o insert
        userDAO.create(u);
        
        if (u.getId() > 0) {
            loggedUser = u; // Auto-login
            System.out.println("Conta criada! Você já está logado.");
            System.out.println("DICA: Complete seu perfil com foto e bio no menu 'Meu Perfil'.");
        } else {
            System.out.println("Erro ao criar usuário.");
        }
    }

    // --- 2. MENU PRINCIPAL (LOGADO) ---

    private static void menuPrincipal() {
        System.out.println("\n=========================================");
        System.out.println("Olá, " + loggedUser.getUsername() + "!");
        System.out.println("=========================================");
        System.out.println("1 - Explorar (Buscar e Listar)");
        System.out.println("2 - Meu Estúdio (Publicar Obras/Exposições)");
        System.out.println("3 - Gerenciar Meu Acervo (Editar/Excluir)");
        System.out.println("4 - Minha Curadoria (Vincular Obras -> Exposições)");
        System.out.println("5 - Meu Perfil");
        System.out.println("0 - Deslogar");
        System.out.print("Escolha: ");

        int op = lerInt();

        switch (op) {
            case 1 -> menuExplorar();
            case 2 -> menuEstudio();
            case 3 -> menuGerenciar();
            case 4 -> menuCuradoria();
            case 5 -> menuPerfil();
            case 0 -> {
                loggedUser = null;
                System.out.println("Deslogando...");
            }
            default -> System.out.println("Opção inválida.");
        }
    }

    // --- 3. EXPLORAR (BUSCA E LISTAGEM) ---

    private static void menuExplorar() {
        System.out.println("\n--- EXPLORAR ---");
        System.out.println("1 - Ver Artistas (Usuários)");
        System.out.println("2 - Ver Galeria de Obras");
        System.out.println("3 - Ver Exposições em Cartaz");
        System.out.println("4 - Ver Avaliações Recentes");
        System.out.println("5 - Buscar Obra por Título");
        System.out.println("0 - Voltar");
        System.out.print(">> ");
        
        int op = lerInt();
        switch (op) {
            case 1 -> userDAO.readAll().forEach(u -> 
                System.out.println("ID: " + u.getId() + " | @" + u.getUsername() + " | " + u.getLocation()));
            
            case 2 -> artworkDAO.readAll().forEach(a -> 
                System.out.println("ID: " + a.getId() + " | Título: " + a.getTitle() + " | Desc: " + a.getDescription()));
            
            case 3 -> exhibitionDAO.readAll().forEach(e -> 
                System.out.println("ID: " + e.getId() + " | Expo: " + e.getName() + " | Tema: " + e.getTheme()));
            
            case 4 -> ratingDAO.readAll().forEach(r -> 
                System.out.println("Nota: " + r.getNote() + "/5 | Comentário: " + r.getText()));

            case 5 -> {
                System.out.print("Digite o termo da busca: ");
                String termo = sc.nextLine().toLowerCase();
                List<Artwork> encontradas = artworkDAO.readAll().stream()
                    .filter(a -> a.getTitle().toLowerCase().contains(termo))
                    .collect(Collectors.toList());
                
                if(encontradas.isEmpty()) System.out.println("Nenhuma obra encontrada.");
                else encontradas.forEach(a -> System.out.println("#" + a.getId() + " " + a.getTitle()));
            }
            case 0 -> {}
        }
    }

    // --- 4. MEU ESTÚDIO (CRIAÇÃO) ---

    private static void menuEstudio() {
        System.out.println("\n--- MEU ESTÚDIO ---");
        System.out.println("1 - Publicar Nova Obra");
        System.out.println("2 - Criar Nova Exposição");
        System.out.println("3 - Sugerir Nova Categoria");
        System.out.println("4 - Escrever uma Avaliação (Review)");
        System.out.println("0 - Voltar");
        System.out.print(">> ");

        int op = lerInt();
        switch (op) {
            case 1 -> criarObra();
            case 2 -> criarExposicao();
            case 3 -> criarCategoria();
            case 4 -> criarAvaliacao();
            case 0 -> {}
        }
    }

    private static void criarObra() {
        System.out.println("\n-- Publicar Obra --");
        Artwork a = new Artwork();
        a.setIdUser(loggedUser.getId()); // Vínculo automático
        
        System.out.print("Título da Obra: ");
        a.setTitle(sc.nextLine());
        System.out.print("Descrição: ");
        a.setDescription(sc.nextLine());
        
        System.out.println("Categorias disponíveis:");
        categoryDAO.readAll().forEach(c -> System.out.println(c.getId() + " - " + c.getName()));
        System.out.print("ID da Categoria: ");
        a.setIdCategory(Long.parseLong(sc.nextLine()));

        artworkDAO.create(a);
    }

    private static void criarExposicao() {
        System.out.println("\n-- Criar Exposição --");
        Exhibition e = new Exhibition();
        e.setIdCreator(loggedUser.getId()); // Vínculo automático

        System.out.print("Nome do Evento: ");
        e.setName(sc.nextLine());
        System.out.print("Tema: ");
        e.setTheme(sc.nextLine());
        System.out.print("Descrição: ");
        e.setDescription(sc.nextLine());
        System.out.print("Data Início (DD/MM/AAAA): ");
        e.setStartDate(sc.nextLine());
        System.out.print("Data Fim (DD/MM/AAAA): ");
        e.setEndDate(sc.nextLine());

        exhibitionDAO.create(e);
    }

    private static void criarCategoria() {
        Category c = new Category();
        System.out.print("Nome da Categoria: ");
        c.setName(sc.nextLine());
        System.out.print("Descrição: ");
        c.setDescription(sc.nextLine());
        categoryDAO.create(c);
    }

    private static void criarAvaliacao() {
        System.out.println("\n-- Avaliar --");
        Rating r = new Rating();
        r.setUserId(loggedUser.getId());

        System.out.print("Você quer avaliar: 1-Obra ou 2-Exposição? ");
        int tipo = lerInt();

        if (tipo == 1) {
            System.out.print("ID da Obra: ");
            r.setArtworkId(Long.parseLong(sc.nextLine()));
        } else if (tipo == 2) {
            System.out.print("ID da Exposição: ");
            r.setExhibitionId(Long.parseLong(sc.nextLine()));
        } else {
            System.out.println("Inválido.");
            return;
        }

        System.out.print("Nota (1 a 5): ");
        r.setNote(lerInt());
        System.out.print("Comentário: ");
        r.setText(sc.nextLine());

        ratingDAO.create(r);
    }

    // --- 5. GERENCIAR ACERVO (UPDATE/DELETE SOMENTE PROPRIETÁRIO) ---

    private static void menuGerenciar() {
        System.out.println("\n--- GERENCIAR MEU ACERVO ---");
        System.out.println("1 - Editar minhas Obras");
        System.out.println("2 - Editar minhas Exposições");
        System.out.println("3 - Excluir Obra");
        System.out.println("4 - Excluir Exposição");
        System.out.println("0 - Voltar");
        System.out.print(">> ");

        int op = lerInt();
        switch (op) {
            case 1 -> editarObra();
            case 2 -> editarExposicao();
            case 3 -> excluirObra();
            case 4 -> excluirExposicao();
            case 0 -> {}
        }
    }

    private static void editarObra() {
        // Filtrar apenas obras do usuário logado
        List<Artwork> minhasObras = artworkDAO.readAll().stream()
            .filter(a -> a.getIdUser() == loggedUser.getId())
            .collect(Collectors.toList());

        if (minhasObras.isEmpty()) {
            System.out.println("Você não possui obras cadastradas.");
            return;
        }

        System.out.println("Suas Obras:");
        minhasObras.forEach(a -> System.out.println(a.getId() + " - " + a.getTitle()));

        System.out.print("Digite o ID da obra para editar: ");
        int id = lerInt();

        // Verificar posse
        Artwork a = minhasObras.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
        if (a == null) {
            System.out.println("Obra não encontrada ou não pertence a você.");
            return;
        }

        System.out.print("Novo Título (ENTER para manter '" + a.getTitle() + "'): ");
        String t = sc.nextLine();
        if (!t.isBlank()) a.setTitle(t);

        System.out.print("Nova Descrição (ENTER para manter): ");
        String d = sc.nextLine();
        if (!d.isBlank()) a.setDescription(d);

        artworkDAO.update(a);
    }

    private static void editarExposicao() {
        List<Exhibition> minhasExpos = exhibitionDAO.readAll().stream()
            .filter(e -> e.getIdCreator() == loggedUser.getId())
            .collect(Collectors.toList());

        if (minhasExpos.isEmpty()) {
            System.out.println("Você não possui exposições.");
            return;
        }

        minhasExpos.forEach(e -> System.out.println(e.getId() + " - " + e.getName()));
        System.out.print("ID da exposição para editar: ");
        int id = lerInt();

        Exhibition e = minhasExpos.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
        if (e == null) {
            System.out.println("Exposição inválida.");
            return;
        }

        System.out.print("Novo Nome (ENTER para manter '" + e.getName() + "'): ");
        String n = sc.nextLine();
        if(!n.isBlank()) e.setName(n);

        System.out.print("Novo Tema (ENTER para manter): ");
        String th = sc.nextLine();
        if(!th.isBlank()) e.setTheme(th);

        exhibitionDAO.update(e);
    }

    private static void excluirObra() {
        System.out.print("ID da obra para EXCLUIR (Atenção!): ");
        int id = lerInt();
        
        Artwork a = artworkDAO.findById(id);
        if (a != null && a.getIdUser() == loggedUser.getId()) {
            artworkDAO.delete(id);
        } else {
            System.out.println("Erro: Obra não existe ou não pertence a você.");
        }
    }

    private static void excluirExposicao() {
        System.out.print("ID da exposição para EXCLUIR: ");
        int id = lerInt();
        
        // Simulação de findById para exposição (já que o DAO retorna lista)
        Exhibition e = exhibitionDAO.readAll().stream()
                .filter(x -> x.getId() == id).findFirst().orElse(null);

        if (e != null && e.getIdCreator() == loggedUser.getId()) {
            exhibitionDAO.delete(id);
        } else {
            System.out.println("Erro: Exposição não existe ou não pertence a você.");
        }
    }

    // --- 6. CURADORIA (RELAÇÃO N:M) ---
    // Aqui simulamos a "Montagem" de uma exposição. O usuário seleciona UMA exposição DELE
    // e adiciona obras (dele ou de outros) ao catálogo.

    private static void menuCuradoria() {
        System.out.println("\n--- CURADORIA DE EXPOSIÇÕES ---");
        System.out.println("1 - Adicionar Obra ao Catálogo da Exposição");
        System.out.println("2 - Ver Obras de uma Exposição");
        System.out.println("0 - Voltar");
        System.out.print(">> ");

        int op = lerInt();
        switch (op) {
            case 1 -> vincularObra();
            case 2 -> {
                System.out.print("ID da Exposição: ");
                long idExp = Long.parseLong(sc.nextLine());
                eaDAO.readAll().stream()
                    .filter(ea -> ea.getExhibitionId() == idExp)
                    .forEach(ea -> System.out.println("Vínculo ID: " + ea.getId() + " | Obra ID: " + ea.getArtworkId()));
            }
            case 0 -> {}
        }
    }

    private static void vincularObra() {
        // 1. Listar exposições DO USUÁRIO
        List<Exhibition> minhasExpos = exhibitionDAO.readAll().stream()
            .filter(e -> e.getIdCreator() == loggedUser.getId())
            .collect(Collectors.toList());

        if (minhasExpos.isEmpty()) {
            System.out.println("Você precisa criar uma exposição primeiro (Menu 'Meu Estúdio').");
            return;
        }

        System.out.println("Selecione sua Exposição:");
        minhasExpos.forEach(e -> System.out.println("ID: " + e.getId() + " | " + e.getName()));
        System.out.print("Digite o ID da Exposição: ");
        long idExpo = Long.parseLong(sc.nextLine());

        // Valida se a expo é dele mesmo
        boolean isOwner = minhasExpos.stream().anyMatch(e -> e.getId() == idExpo);
        if (!isOwner) {
            System.out.println("Exposição inválida.");
            return;
        }

        // 2. Pedir ID da Obra (pode ser de qualquer pessoa - "Curadoria")
        System.out.print("Digite o ID da Obra para adicionar ao catálogo: ");
        long idObra = Long.parseLong(sc.nextLine());
        
        // (Opcional: Verificar se obra existe via DAO, mas vamos confiar no ID para agilizar o exemplo)
        
        ExhibitionArtwork ea = new ExhibitionArtwork();
        ea.setExhibitionId(idExpo);
        ea.setArtworkId(idObra);
        
        eaDAO.create(ea);
    }

    // --- 7. PERFIL ---

    private static void menuPerfil() {
        System.out.println("\n--- MEU PERFIL ---");
        System.out.println("Nome: " + loggedUser.getUsername());
        System.out.println("Bio: " + loggedUser.getBiography());
        System.out.println("1 - Atualizar Bio e Foto");
        System.out.println("2 - Deletar minha conta");
        System.out.println("0 - Voltar");
        
        int op = lerInt();
        if (op == 1) {
            System.out.print("Nova Bio: ");
            loggedUser.setBiography(sc.nextLine());
            System.out.print("URL Foto: ");
            loggedUser.setProfilePhoto(sc.nextLine());
            userDAO.update(loggedUser);
        } else if (op == 2) {
            System.out.print("Tem certeza? Digite 1 para confirmar: ");
            if (lerInt() == 1) {
                userDAO.delete((int) loggedUser.getId());
                loggedUser = null;
                System.out.println("Conta deletada.");
            }
        }
    }

    // --- UTILS ---
    private static int lerInt() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }
}