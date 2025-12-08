package br.edu.ifpr.view;

import java.util.List;
import java.util.stream.Collectors;
import br.edu.ifpr.controller.ArtworkController;
import br.edu.ifpr.controller.CategoryController;
import br.edu.ifpr.controller.UserController;
import br.edu.ifpr.model.Artwork;
import br.edu.ifpr.model.Category;

public class CategoryView {

    private CategoryController categoryController;
    private ArtworkController artworkController;
    private UserController userController;

    public CategoryView(CategoryController cC, ArtworkController aC, UserController uC) {
        this.categoryController = cC;
        this.artworkController = aC;
        this.userController = uC;
    }

    public void menuNavegarCategorias() {
        System.out.println("\n=== CATEGORIAS ===");
        List<Category> cats = categoryController.listCategories();
        cats.forEach(c -> System.out.println("ID [" + c.getId() + "] " + c.getName()));
        
        System.out.println("\nOpcoes:");
        System.out.println("1. Entrar em uma Categoria (Ver Obras)");
        System.out.println("2. Sugerir Nova Categoria");
        System.out.println("0. Voltar");
        System.out.print(">> ");
        
        int op = ViewUtils.lerInteiro();
        if (op == 1) {
            System.out.print("Digite o ID da categoria: ");
            long catId = Long.parseLong(ViewUtils.sc.nextLine());
            
            List<Artwork> obrasDaCategoria = artworkController.listArtworks().stream()
                .filter(a -> a.getIdCategory() == catId) 
                .collect(Collectors.toList());
            
            if (obrasDaCategoria.isEmpty()) {
                System.out.println("Nenhuma obra encontrada nesta categoria.");
            } else {
                System.out.println("\n--- Obras na Categoria " + catId + " ---");
                obrasDaCategoria.forEach(a -> ViewUtils.mostrarObraDetalhada(a, userController, categoryController));
            }
            
        } else if (op == 2) {
            Category c = new Category();
            System.out.print("Nome: ");
            c.setName(ViewUtils.sc.nextLine());
            System.out.print("Descricao: ");
            c.setDescription(ViewUtils.sc.nextLine());
            categoryController.createCategory(c);
        }
    }
}