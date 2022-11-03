package com.example.cs5520_project;

import java.util.HashMap;

public class UserInfo {

    String name, username, userId;
    HashMap<String, Integer> sentStickers, receivedStickers;

    public UserInfo() {
    }

    public UserInfo(String userId, String name, String username) {
        this.name = name;
        this.username = username;
        this.sentStickers =  new HashMap<String, Integer>();
        this.receivedStickers =  new HashMap<String, Integer>();
        for(int i=1; i<11; i++){
            sentStickers.put("Emoji " + i, 0);
            receivedStickers.put("Emoji " + i, 0);
        }
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

    public HashMap<String, Integer> getSentStickers() {
        return sentStickers;
    }

    public HashMap<String, Integer> getReceivedStickers() {
        return receivedStickers;
    }

}
