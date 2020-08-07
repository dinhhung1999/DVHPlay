package com.example.dvhplay.Models;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoUlti implements Serializable {
    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("category")
    @Expose
    String category;
    @SerializedName("classify")
    @Expose
    String classify;
    @SerializedName("category_id")
    @Expose
    String category_id;
    @SerializedName("provider_id")
    @Expose
    String provider_id;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("avatar")
    @Expose
    String avatar;
    @SerializedName("artist_name")
    @Expose
    String artist_name;
    @SerializedName("file_mp4")
    @Expose
    String file_mp4;
    @SerializedName("file_mp4_size")
    @Expose
    String file_mp4_size;
    @SerializedName("date_created")
    @Expose
    String date_created;
    @SerializedName("date_modified")
    @Expose
    String date_modified;
    @SerializedName("date_published")
    @Expose
    String date_published;
    @SerializedName("episode")
    @Expose
    String episode;  // thời lượng
    @SerializedName("synopsis")
    @Expose
    String synopsis; // tóm tắt
    @SerializedName("video_duration")
    @Expose
    String video_duration;
    @SerializedName("nation")
    @Expose
    String nation;

    public VideoUlti(int id, String category, String classify, String category_id, String provider_id, String title, String avatar, String artist_name, String file_mp4, String file_mp4_size, String date_created, String date_modified, String date_published, String episode, String synopsis, String video_duration, String nation) {
        this.id = id;
        this.category = category;
        this.classify = classify;
        this.category_id = category_id;
        this.provider_id = provider_id;
        this.title = title;
        this.avatar = avatar;
        this.artist_name = artist_name;
        this.file_mp4 = file_mp4;
        this.file_mp4_size = file_mp4_size;
        this.date_created = date_created;
        this.date_modified = date_modified;
        this.date_published = date_published;
        this.episode = episode;
        this.synopsis = synopsis;
        this.video_duration = video_duration;
        this.nation = nation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
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

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getFile_mp4() {
        return file_mp4;
    }

    public void setFile_mp4(String file_mp4) {
        this.file_mp4 = file_mp4;
    }

    public String getFile_mp4_size() {
        return file_mp4_size;
    }

    public void setFile_mp4_size(String file_mp4_size) {
        this.file_mp4_size = file_mp4_size;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(String date_modified) {
        this.date_modified = date_modified;
    }

    public String getDate_published() {
        return date_published;
    }

    public void setDate_published(String date_published) {
        this.date_published = date_published;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getVideo_duration() {
        return video_duration;
    }

    public void setVideo_duration(String video_duration) {
        this.video_duration = video_duration;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }
}