package com.example.dvhplay.Models;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoUlti implements Serializable {
    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("user_id")
    @Expose
    int user_id;
    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("avatar")
    @Expose
    String avatar;

    @SerializedName("file_mp4")
    @Expose
    String file_mp4;
    @SerializedName("date_published")
    @Expose
    String date_published;

//    @SerializedName("category")
//    @Expose
//    String category;
//    @SerializedName("classify")
//    @Expose
//    String classify;
//    @SerializedName("category_id")
//    @Expose
//    String category_id;
//    @SerializedName("provider_id")
//    @Expose
//    String provider_id;
//    @SerializedName("artist_name")
//    @Expose
//    String artist_name;
//    @SerializedName("file_mp4_size")
//    @Expose
//    String file_mp4_size;
//    @SerializedName("date_created")
//    @Expose
//    String date_created;
//    @SerializedName("date_modified")
//    @Expose
//    String date_modified;
//    @SerializedName("date_published")
//    @Expose
//    String date_published;
//    @SerializedName("episode")
//    @Expose
//    String episode;  // thời lượng
//    @SerializedName("synopsis")
//    @Expose
//    String synopsis; // tóm tắt
//    @SerializedName("video_duration")
//    @Expose
//    String video_duration;
//    @SerializedName("nation")
//    @Expose
//    String nation;


    public VideoUlti(int id, int user_id, String title, String avatar, String file_mp4, String date_published) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.avatar = avatar;
        this.file_mp4 = file_mp4;
        this.date_published = date_published;
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

    public String getDate_published() {
        return date_published;
    }

    public void setDate_published(String date_published) {
        this.date_published = date_published;
    }
}