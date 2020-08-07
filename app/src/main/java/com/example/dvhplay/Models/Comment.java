package com.example.dvhplay.Models;

public class Comment {
    int id;
    int user_id;
    int video_id;
    String username;
    String content;

    public Comment(int id, int user_id, int video_id, String username, String content) {
        this.id = id;
        this.user_id = user_id;
        this.video_id = video_id;
        this.username = username;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
