package br.edu.ifpr.model;

public class ExhibitionArtwork {

    private Long id;
    private Long exhibitionId;
    private Long artworkId;

    public ExhibitionArtwork() {
    }

    public ExhibitionArtwork(Long id, Long exhibitionId, Long artworkId) {
        this.id = id;
        this.exhibitionId = exhibitionId;
        this.artworkId = artworkId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(Long exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public Long getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(Long artworkId) {
        this.artworkId = artworkId;
    }
}
