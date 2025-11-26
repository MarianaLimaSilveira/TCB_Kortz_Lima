package br.edu.ifpr.controller;

import java.sql.Connection;
import java.util.List;

import br.edu.ifpr.dao.ExhibitionArtworkDAO;
import br.edu.ifpr.dao.ExhibitionDAO;
import br.edu.ifpr.dao.RatingDAO;
import br.edu.ifpr.model.Exhibition;

public class ExhibitionController {

    private ExhibitionDAO exhibitionDAO = new ExhibitionDAO();
    private RatingDAO ratingDAO = new RatingDAO();
    private ExhibitionArtworkDAO eaDAO;

    public ExhibitionController(Connection conn) {
        this.eaDAO = new ExhibitionArtworkDAO(conn);
    }

    public void createExhibition(Exhibition exhibition) {
        exhibitionDAO.create(exhibition);
    }

    public List<Exhibition> listExhibitions() {
        return exhibitionDAO.readAll();
    }

    public void updateExhibition(Exhibition exhibition) {
        exhibitionDAO.update(exhibition);
    }

    public void deleteExhibition(int exId) {

        boolean temRating = ratingDAO.readAll()
                .stream().anyMatch(r -> r.getExhibitionId() != null && r.getExhibitionId() == exId);

        boolean temObraVinculada = eaDAO.readAll()
                .stream().anyMatch(ea -> ea.getExhibitionId() == exId);

        if (temRating || temObraVinculada) {
            System.out.println("Exposição não pode ser excluída pois possui vínculos ou avaliações!");
            return;
        }
        exhibitionDAO.delete(exId);
    }
}
