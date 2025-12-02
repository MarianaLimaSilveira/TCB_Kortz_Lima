package br.edu.ifpr.model;

public class Rating {
    private Long id;
    private Long id_artwork;
    private Long id_exhibition;
    private Long id_user;
    private Integer note;
    private String text;

    public Rating() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArtworkId() {
        return id_artwork;
    }

    public void setArtworkId(Long id_artwork) {
        this.id_artwork = id_artwork;
    }

    public Long getExhibitionId() {
        return id_exhibition;
    }

    public void setExhibitionId(Long id_exhibition) {
        this.id_exhibition = id_exhibition;
    }

    public Long getUserId() {
        return id_user;
    }

    public void setUserId(Long id_user) {
        this.id_user = id_user;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
