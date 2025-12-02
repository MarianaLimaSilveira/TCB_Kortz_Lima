package br.edu.ifpr.model;

public class Artwork {

    private long id;
    private String title;
    private String description;
    private long id_category;
    private long id_user;

    public Artwork() {
    }

    public Artwork(long id, String title, String description,
            long id_category, long id_user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.id_category = id_category;
        this.id_user = id_user;
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
        return id_category;
    }

    public void setIdCategory(long id_category) {
        this.id_category = id_category;
    }

    public long getIdUser() {
        return id_user;
    }

    public void setIdUser(long id_user) {
        this.id_user = id_user;
    }
}
