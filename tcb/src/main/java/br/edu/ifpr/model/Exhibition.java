package br.edu.ifpr.model;

public class Exhibition {

    private int id;
    private int idCreator;
    private String name;
    private String theme;
    private String description;
    private String startDate;
    private String endDate;

    public Exhibition() {
    }

    public Exhibition(int id, int idCreator, String name,
            String theme, String description, String startDate, String endDate) {
        this.id = id;
        this.idCreator = idCreator;
        this.name = name;
        this.theme = theme;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCreator() {
        return idCreator;
    }

    public void setIdCreator(int idCreator) {
        this.idCreator = idCreator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}