package br.edu.ifpr.model;

public class Category {
    private Long id;
    private String name;
    private String description;
    /*
     * private boolean approved; // só entra no sistema após aprovação por moderador
     * private Long requestedByUserId; // se foi solicitada por um usuário
     */

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*
     * public boolean isApproved() { return approved; }
     * public void setApproved(boolean approved) { this.approved = approved; }
     * 
     * public Long getRequestedByUserId() { return requestedByUserId; }
     * public void setRequestedByUserId(Long requestedByUserId) {
     * this.requestedByUserId = requestedByUserId; }
     */
}
