package com.example.example.entity;

public class User {
    private int userId;
    private String userName;
    private String userNote;
    private String cloudName;

    public User() {
    }

    public User(int userId, String userName, String userNote, String cloudName) {
        this.userId = userId;
        this.userName = userName;
        this.userNote = userNote;
        this.cloudName = cloudName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public String getCloudName() {
        return cloudName;
    }

    public void setCloudName(String cloudName) {
        this.cloudName = cloudName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userNote='" + userNote + '\'' +
                ", cloudName='" + cloudName + '\'' +
                '}';
    }
}
