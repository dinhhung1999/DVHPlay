package com.example.dvhplay.Models;

public class SearchHistory {
    int user_id;
    String title;

    public SearchHistory(int user_id, String title) {
        this.user_id = user_id;
        this.title = title;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
