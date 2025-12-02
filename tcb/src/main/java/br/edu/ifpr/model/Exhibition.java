package br.edu.ifpr.model;

public class Exhibition {

    private long id;
    private long id_creator;
    private String name;
    private String theme;
    private String description;
    private String startDate;
    private String endDate;

    public Exhibition() {
    }

    public Exhibition(long id, long id_creator, String name,
            String theme, String description, String startDate, String endDate) {
        this.id = id;
        this.id_creator = id_creator;
        this.name = name;
        this.theme = theme;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdCreator() {
        return id_creator;
    }

    public void setIdCreator(long id_creator) {
        this.id_creator = id_creator;
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