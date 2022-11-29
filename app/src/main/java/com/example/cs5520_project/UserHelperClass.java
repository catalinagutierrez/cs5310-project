package com.example.cs5520_project;


import java.util.ArrayList;


public class UserHelperClass {
    String username, email, password, confirmpassword;
    ArrayList<EventHelperClass> addedEventList;


    public UserHelperClass(String username, String email, String password, String confirmpassword, ArrayList<EventHelperClass> addedEventList) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmpassword = confirmpassword;

        this.addedEventList = addedEventList;
    }

    public ArrayList<EventHelperClass> getAddedEventList() {
        return addedEventList;
    }

    public void setAddedEventList(ArrayList<EventHelperClass> addedEventList) {
        this.addedEventList = addedEventList;
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
