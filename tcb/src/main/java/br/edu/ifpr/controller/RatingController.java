package br.edu.ifpr.controller;

import java.util.List;

import br.edu.ifpr.dao.RatingDAO;
import br.edu.ifpr.model.Rating;

public class RatingController {

    private RatingDAO ratingDAO = new RatingDAO();

    public void createRating(Rating rating) {

        if (rating.getNote() < 1 || rating.getNote() > 5) {
            System.out.println("Nota inválida. Permitido apenas entre 1 e 5.");
            return;
        }

        boolean jaExiste = ratingDAO.readAll().stream()
                .anyMatch(r -> r.getUserId() == rating.getUserId() &&
                        ((r.getArtworkId() != null && r.getArtworkId().equals(rating.getArtworkId())) ||
                                (r.getExhibitionId() != null && r.getExhibitionId().equals(rating.getExhibitionId()))));

        if (jaExiste) {
            System.out.println("Usuário já avaliou este item.");
            return;
        }

        ratingDAO.create(rating);
    }

    public List<Rating> listRatings() {
        return ratingDAO.readAll();
    }

    public void updateRating(Rating rating) {
        ratingDAO.update(rating);
    }

    public void deleteRating(Long id) {
        ratingDAO.delete(id);
    }
}
