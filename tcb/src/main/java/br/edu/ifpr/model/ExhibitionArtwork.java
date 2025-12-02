package br.edu.ifpr.model;

public class ExhibitionArtwork {

    private Long id;
    private Long id_exhibition;
    private Long id_artwork;

    public ExhibitionArtwork() {
    }

    public ExhibitionArtwork(Long id, Long id_exhibition, Long id_artwork) {
        this.id = id;
        this.id_exhibition = id_exhibition;
        this.id_artwork = id_artwork;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExhibitionId() {
        return id_exhibition;
    }

    public void setExhibitionId(Long id_exhibition) {
        this.id_exhibition = id_exhibition;
    }

    public Long getArtworkId() {
        return id_artwork;
    }

    public void setArtworkId(Long id_artwork) {
        this.id_artwork = id_artwork;
    }
}
