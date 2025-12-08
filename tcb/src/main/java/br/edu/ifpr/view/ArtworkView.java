package br.edu.ifpr.view;

import java.util.List;
import java.util.stream.Collectors;
import br.edu.ifpr.controller.*;
import br.edu.ifpr.model.*;

public class ArtworkView {

    private ArtworkController artworkController;
    private CategoryController categoryController;
    private UserController userController;

    public ArtworkView(ArtworkController aC, CategoryController cC, UserController uC) {
        this.artworkController = aC;
        this.categoryController = cC;
        this.userController = uC;
    }

    public void menuMinhasObras() {
        System.out.println("\n=== MEU ESTUDIO ===");
        long meuId = Sessao.getIdUsuario();

        List<Artwork> minhasObras = artworkController.listArtworks().stream()
                .filter(a -> a.getIdUser() == meuId)
                .collect(Collectors.toList());

        if (minhasObras.isEmpty()) System.out.println("(Voce ainda nao postou nenhuma obra)");
        else minhasObras.forEach(a -> ViewUtils.mostrarObraDetalhada(a, userController, categoryController));

        System.out.println("\n1. Nova Obra");
        System.out.println("2. Editar Obra");
        System.out.println("3. Excluir Obra");
        System.out.println("0. Voltar");
        System.out.print(">> ");

        int op = ViewUtils.lerInteiro();
        switch(op) {
            case 1 -> criarObra(meuId);
            case 2 -> editarObra(minhasObras);
            case 3 -> excluirObra(minhasObras);
        }
    }

    private void criarObra(long meuId) {
        Artwork a = new Artwork();
        a.setIdUser((int) meuId);
        
        System.out.print("Titulo: ");
        a.setTitle(ViewUtils.sc.nextLine());
        System.out.print("Descricao: ");
        a.setDescription(ViewUtils.sc.nextLine());
        
        System.out.println("=== Escolha a Categoria ===");
        List<Category> allCats = categoryController.listCategories();
        allCats.forEach(c -> System.out.println(c.getId() + " - " + c.getName()));
        
        System.out.print("ID da Categoria (0 ou Enter para 'Nenhuma'): ");
        int idCat = ViewUtils.lerInteiro();
        
        if (idCat <= 0) {
            Category catNenhuma = allCats.stream()
                .filter(c -> c.getName().equalsIgnoreCase("Nenhuma"))
                .findFirst().orElse(null);
            
            if (catNenhuma != null) a.setIdCategory(catNenhuma.getId());
            else {
                System.out.println("Aviso: Categoria 'Nenhuma' nao encontrada.");
                return;
            }
        } else {
            a.setIdCategory((long) idCat);
        }
        artworkController.createArtwork(a);
    }

    private void editarObra(List<Artwork> minhasObras) {
        System.out.print("ID da obra para editar: ");
        int idObra = ViewUtils.lerInteiro();
        Artwork a = minhasObras.stream().filter(o -> o.getId() == idObra).findFirst().orElse(null);
        
        if (a != null) {
            System.out.print("Novo Titulo (ENTER pula): ");
            String novoTitulo = ViewUtils.sc.nextLine();
            if(!novoTitulo.isEmpty()) a.setTitle(novoTitulo);

            System.out.print("Nova Descricao (ENTER pula): ");
            String novaDesc = ViewUtils.sc.nextLine();
            if(!novaDesc.isEmpty()) a.setDescription(novaDesc);
            
            artworkController.updateArtwork(a);
        } else {
            System.out.println("Obra nao encontrada.");
        }
    }

    private void excluirObra(List<Artwork> minhasObras) {
        System.out.print("ID da obra para excluir: ");
        int idObra = ViewUtils.lerInteiro();
        if (minhasObras.stream().anyMatch(o -> o.getId() == idObra)) {
            artworkController.deleteArtwork(idObra);
        } else {
            System.out.println("Permissao negada ou nao encontrada.");
        }
    }
}