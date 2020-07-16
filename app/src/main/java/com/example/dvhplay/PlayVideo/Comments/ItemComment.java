package com.example.dvhplay.PlayVideo.Comments;

public class ItemComment {
    String userName;
    String Comment;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public ItemComment(String userName, String comment) {
        this.userName = userName;
        Comment = comment;
    }
}
