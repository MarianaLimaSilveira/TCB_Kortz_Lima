package br.edu.ifpr.controller;

import java.sql.Connection;
import java.util.List;

import br.edu.ifpr.dao.ExhibitionArtworkDAO;
import br.edu.ifpr.dao.ExhibitionDAO;
import br.edu.ifpr.dao.RatingDAO;
import br.edu.ifpr.model.Exhibition;
// imports...

public class ExhibitionController {

    private ExhibitionDAO exhibitionDAO = new ExhibitionDAO();
    private RatingDAO ratingDAO = new RatingDAO();
    private ExhibitionArtworkDAO eaDAO;

    public ExhibitionController(Connection conn) {
        this.eaDAO = new ExhibitionArtworkDAO(conn);
    }

    // CRUD Básico...
    public void createExhibition(Exhibition exhibition) {
        exhibitionDAO.create(exhibition);
    }

    public List<Exhibition> listExhibitions() {
        return exhibitionDAO.readAll();
    }

    public void updateExhibition(Exhibition exhibition) {
        exhibitionDAO.update(exhibition);
    }

    // Exclusão com Validação de Integridade
    public void deleteExhibition(int id_exhibition) {

        // 1. Verifica se a exposição tem avaliações
        boolean temRating = ratingDAO.readAll()
                .stream().anyMatch(r -> r.getExhibitionId() != null && r.getExhibitionId() == id_exhibition);

        // 2. Verifica se a exposição tem obras vinculadas (tabela N:M)
        boolean temObraVinculada = eaDAO.readAll()
                .stream().anyMatch(ea -> ea.getExhibitionId() == id_exhibition);

        // Bloqueia a exclusão se houver dependências para manter a integridade do banco
        if (temRating || temObraVinculada) {
            System.out.println("Exposição não pode ser excluída pois possui vínculos ou avaliações!");
            return;
        }
        exhibitionDAO.delete(id_exhibition);
    }
}