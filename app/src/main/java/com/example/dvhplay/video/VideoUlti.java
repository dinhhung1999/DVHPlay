package com.example.dvhplay.video;

import java.io.Serializable;

public class VideoUlti implements Serializable {
    String id;
    String category;
    String classify;
    String category_id;
    String provider_id;
    String title;
    String avatarUrl;
    String artist_name;
    String urlVideo;
    String file_mp4_size;
    String date_created;
    String date_modified;
    String date_published;
    String episode;  // thời lượng
    String synopsis; // tóm tắt
    String video_duration;
    String nation;

    public VideoUlti(String id, String category, String classify, String category_id, String provider_id, String title, String avatarUrl, String artist_name, String urlVideo, String file_mp4_size, String date_created, String date_modified, String date_published, String episode, String synopsis, String video_duration, String nation) {
        this.id = id;
        this.category = category;
        this.classify = classify;
        this.category_id = category_id;
        this.provider_id = provider_id;
        this.title = title;
        this.avatarUrl = avatarUrl;
        this.artist_name = artist_name;
        this.urlVideo = urlVideo;
        this.file_mp4_size = file_mp4_size;
        this.date_created = date_created;
        this.date_modified = date_modified;
        this.date_published = date_published;
        this.episode = episode;
        this.synopsis = synopsis;
        this.video_duration = video_duration;
        this.nation = nation;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getClassify() {
        return classify;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public String getTitle() {
        return title;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public String getFile_mp4_size() {
        return file_mp4_size;
    }

    public String getDate_created() {
        return date_created;
    }

    public String getDate_modified() {
        return date_modified;
    }

    public String getDate_published() {
        return date_published;
    }

    public String getEpisode() {
        return episode;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getVideo_duration() {
        return video_duration;
    }

    public String getNation() {
        return nation;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public void setFile_mp4_size(String file_mp4_size) {
        this.file_mp4_size = file_mp4_size;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public void setDate_modified(String date_modified) {
        this.date_modified = date_modified;
    }

    public void setDate_published(String date_published) {
        this.date_published = date_published;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setVideo_duration(String video_duration) {
        this.video_duration = video_duration;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }
}
