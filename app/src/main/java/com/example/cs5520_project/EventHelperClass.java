package com.example.cs5520_project;

public class EventHelperClass {

    String image;
    String description;
    String title;

    public EventHelperClass(String image, String description, String title) {
        this.image = image;
        this.description = description;
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }
}
