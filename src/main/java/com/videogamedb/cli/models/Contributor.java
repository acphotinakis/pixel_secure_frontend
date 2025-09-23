package com.videogamedb.cli.models;

public class Contributor {
    private String id;
    private String contributorName;
    private String type;

    public Contributor() {
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContributorName() {
        return contributorName;
    }

    public void setContributorName(String contributorName) {
        this.contributorName = contributorName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}