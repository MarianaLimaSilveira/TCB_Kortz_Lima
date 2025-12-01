package br.edu.ifpr.controller;

import java.util.List;

import br.edu.ifpr.dao.ArtworkDAO;
import br.edu.ifpr.dao.CategoryDAO;
import br.edu.ifpr.model.Category;

public class CategoryController {

    private CategoryDAO categoryDAO = new CategoryDAO();
    private ArtworkDAO artworkDAO = new ArtworkDAO();

    public void createCategory(Category category) {
        categoryDAO.create(category);
    }

    public List<Category> listCategories() {
        return categoryDAO.readAll();
    }

    public void updateCategory(Category category) {
        categoryDAO.update(category);
    }

    public void deleteCategory(Long categoryId) {

        boolean categoriaEmUso = artworkDAO.readAll().stream()
                .anyMatch(a -> a.getIdCategory().equalsIgnoreCase(
                        listCategories().stream().filter(c -> c.getId().equals(categoryId)).findFirst().get().getName()
                ));

        if (categoriaEmUso) {
            System.out.println("Categoria está em uso por obras e não pode ser excluída. Apenas desativação permitida.");
            return;
        }
        categoryDAO.delete(categoryId);
    }
}
