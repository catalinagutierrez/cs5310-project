package com.example.cs5520_project;

public class EventHelperClass {
    String image;
    String description;
    String title;
    String link;

    public EventHelperClass(String image, String description, String title, String link) {
        this.image = image;
        this.description = description;
        this.title = title;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
