package com.example.codehub;

public class LearningPath {

    private String title;
    private int iconResource;
    private String description;

    public LearningPath(String title, int iconResource, String description) {
        this.title = title;
        this.iconResource = iconResource;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public int getIconResource() {
        return iconResource;
    }

    public String getDescription() {
        return description;
    }
}
