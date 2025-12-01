package br.edu.ifpr.model;

public class Artwork {

    private long id;
    private String title;
    private String description;
    private long idCategory;
    private long idUser;

    public Artwork() {
    }

    public Artwork(long id, String title, String description,
            long idCategory, long idUser) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.idCategory = idCategory;
        this.idUser = idUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(long idCategory) {
        this.idCategory = idCategory;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }
}
