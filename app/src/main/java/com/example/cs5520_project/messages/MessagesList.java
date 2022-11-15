package com.example.cs5520_project.messages;

public class MessagesList {

    private String name, lastMessage, profileImage;
    private int unseenMessages;


    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public MessagesList(String name, String lastMessage, String profileImage, int unseenMessages) {
        this.name = name;
        this.lastMessage = lastMessage;
        this.profileImage = profileImage;
        this.unseenMessages = unseenMessages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }

    public void setUnseenMessages(int unseenMessages) {
        this.unseenMessages = unseenMessages;
    }
}
