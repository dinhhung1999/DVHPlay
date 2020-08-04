package com.example.dvhplay.Models;

public class SearchHistory {
//    String id;
    String title;
//    String date;

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }

    public SearchHistory(String title) {
        this.title = title;
    }
}
