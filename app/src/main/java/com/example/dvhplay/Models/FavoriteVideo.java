package com.example.dvhplay.Models;

public class FavoriteVideo {
    int id;
    int user_id;
    int video_id;
    String title;
    String avatar;
    String file_mp4;

    public FavoriteVideo(int id, int user_id, int video_id, String title, String avatar, String file_mp4) {
        this.id = id;
        this.user_id = user_id;
        this.video_id = video_id;
        this.title = title;
        this.avatar = avatar;
        this.file_mp4 = file_mp4;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFile_mp4() {
        return file_mp4;
    }

    public void setFile_mp4(String file_mp4) {
        this.file_mp4 = file_mp4;
    }
}
