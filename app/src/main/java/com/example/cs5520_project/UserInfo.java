package com.example.cs5520_project;

public class UserInfo {

    String name, username;
    int numOfStickers;

    public UserInfo() {
    }

    public UserInfo(String name, String username, int numOfStickers) {
        this.name = name;
        this.username = username;
        this.numOfStickers = numOfStickers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumOfStickers() {
        return numOfStickers;
    }

    public void setNumOfStickers(int numOfStickers) {
        this.numOfStickers = numOfStickers;
    }
}
