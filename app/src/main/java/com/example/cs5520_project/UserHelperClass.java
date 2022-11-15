package com.example.cs5520_project;

import com.example.cs5520_project.messages.MessagesList;

import java.util.List;

public class UserHelperClass {
    String uid,username, email, password, confirmpassword;
    List<MessagesList> messagesLists;



    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<MessagesList> getMessagesLists() {
        return messagesLists;
    }

    public void setMessagesLists(List<MessagesList> messagesLists) {
        this.messagesLists = messagesLists;
    }

    public UserHelperClass(String uid, String username, String email, String password, String confirmpassword, List<MessagesList> messagesLists) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmpassword = confirmpassword;
        this.messagesLists = messagesLists;
        this.uid = uid;
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
