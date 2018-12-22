package com.example.mahmoudahmed.masroqaapp;

public class Posts {
    private String postText;
    private String postUser;
    private long postTime;

    public Posts(String postText, String postUser) {
        this.postText = postText;
        this.postUser = postUser;

        postTime = (int)(System.currentTimeMillis()/1000);
    }

    public Posts() {
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostUser() {
        return postUser;
    }

    public void setPostUser(String postUser) {
        this.postUser = postUser;
    }

    public long getPostTime() {
        return postTime;
    }

    public void setPostTime(long postTime) {
        this.postTime = postTime;
    }
}
