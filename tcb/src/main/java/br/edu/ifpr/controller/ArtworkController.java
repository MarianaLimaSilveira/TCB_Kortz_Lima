package br.edu.ifpr.controller;

import java.sql.Connection;
import java.util.List;

import br.edu.ifpr.dao.ArtworkDAO;
import br.edu.ifpr.dao.ExhibitionArtworkDAO;
import br.edu.ifpr.dao.RatingDAO;
import br.edu.ifpr.model.Artwork;

public class ArtworkController {

    private ArtworkDAO artworkDAO = new ArtworkDAO();
    private RatingDAO ratingDAO = new RatingDAO();
    private ExhibitionArtworkDAO eaDAO;

    public ArtworkController(Connection conn) {
        this.eaDAO = new ExhibitionArtworkDAO(conn);
    }

    public void createArtwork(Artwork artwork) {
        artworkDAO.create(artwork);
    }

    public List<Artwork> listArtworks() {
        return artworkDAO.readAll();
    }

    public void updateArtwork(Artwork artwork) {
        artworkDAO.update(artwork);
    }

    public void deleteArtwork(int id_artwork) {

        boolean temRating = ratingDAO.readAll().stream()
                .anyMatch(r -> r.getArtworkId() != null && r.getArtworkId() == id_artwork);

        boolean temExposicao = eaDAO.readAll().stream()
                .anyMatch(ea -> ea.getArtworkId() == id_artwork);

        if (temRating || temExposicao) {
            System.out.println("Não é permitido excluir uma obra vinculada a exposição ou avaliações.");
            return;
        }
        artworkDAO.delete(id_artwork);
    }
}
