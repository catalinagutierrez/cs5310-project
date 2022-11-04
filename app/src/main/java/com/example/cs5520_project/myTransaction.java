package com.example.cs5520_project;

import java.io.Serializable;

public class myTransaction implements Serializable {
    String sender, receiver, sticker, date;

    public myTransaction() {
    }

    public myTransaction(String sender, String receiver, String sticker, String date){
        this.sender = sender;
        this.receiver = receiver;
        this.sticker = sticker;
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSticker() {
        return sticker;
    }

    public String getDate() {
        return date;
    }


}
