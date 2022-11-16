package com.example.cs5520_project;

import com.example.cs5520_project.messages.MessagesList;

import java.util.ArrayList;
import java.util.List;

public class UserHelperClass {
    String username, email, password, confirmpassword;
    List<MessagesList> messagesLists;
    ArrayList<EventHelperClass> addedEventList;


    public UserHelperClass(String username, String email, String password, String confirmpassword, List<MessagesList> messagesLists, ArrayList<EventHelperClass> addedEventList) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmpassword = confirmpassword;
        this.messagesLists = messagesLists;
        this.addedEventList = addedEventList;
    }

    public ArrayList<EventHelperClass> getAddedEventList() {
        return addedEventList;
    }

    public void setAddedEventList(ArrayList<EventHelperClass> addedEventList) {
        this.addedEventList = addedEventList;
    }

    public List<MessagesList> getMessagesLists() {
        return messagesLists;
    }

    public void setMessagesLists(List<MessagesList> messagesLists) {
        this.messagesLists = messagesLists;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }
}
