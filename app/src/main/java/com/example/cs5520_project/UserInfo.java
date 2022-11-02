package com.example.cs5520_project;

import java.util.HashMap;

public class UserInfo {

    String name, username, userId;
    HashMap<String, Integer> sentStickers, receivedStickers;
    int numOfStickers;

    public UserInfo() {
    }

    public UserInfo(String userId, String name, String username) {
        this.name = name;
        this.username = username;
        this.numOfStickers = 0;
       this.sentStickers =  new HashMap<String, Integer>();
        this.receivedStickers =  new HashMap<String, Integer>();
        sentStickers.put("test", 5);
    }

    public UserInfo(String userId, String name, String username, int numOfStickers) {
        this.name = name;
        this.username = username;
        this.numOfStickers = numOfStickers;
        this.sentStickers =  new HashMap<String, Integer>();
        this.receivedStickers =  new HashMap<String, Integer>();
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
        return 0;
    }

    public void setNumOfStickers(int numOfStickers) {
        this.numOfStickers = numOfStickers;
    }

    public HashMap<String, Integer> getSentStickers() {
        return sentStickers;
    }

    public HashMap<String, Integer> getReceivedStickers() {
        return receivedStickers;
    }

}
