package com.example.codehub;

import java.io.Serializable;

public class LearningPath implements Serializable {

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
