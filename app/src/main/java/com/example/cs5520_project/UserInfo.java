package com.example.cs5520_project;

import java.io.Serializable;
import java.util.HashMap;

public class UserInfo implements Serializable {

    String name, username, uid;
    HashMap<String, Long> sentStickers, receivedStickers;

    public UserInfo() {
    }

    public UserInfo(String uid, String name, String username) {
        this.uid = uid;
        this.name = name;
        this.username = username;
        this.sentStickers =  new HashMap<String, Long>();
        this.receivedStickers =  new HashMap<String, Long>();
        for(int i=1; i<11; i++){
            sentStickers.put("Emoji " + i, 0L);
            receivedStickers.put("Emoji " + i, 0L);
        }
    }

    public UserInfo(String uid, String name, String username, HashMap<String, Long> sentStickers, HashMap<String, Long> receivedStickers) {
        this.uid = uid;
        this.name = name;
        this.username = username;
        this.sentStickers =  sentStickers;
        this.receivedStickers =  receivedStickers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void incrementStickerCount(String type, String stickerName){
        if(type == "sent"){
            sentStickers.put(stickerName, sentStickers.get(stickerName) + 1);
        }else if (type== "received"){
            receivedStickers.put(stickerName, receivedStickers.get(stickerName) + 1);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public HashMap<String, Long> getSentStickers() {
        return sentStickers;
    }

    public HashMap<String, Long> getReceivedStickers() {
        return receivedStickers;
    }

}
