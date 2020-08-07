package com.example.dvhplay.myMedia;

import java.io.Serializable;

public class VideoInDevice implements Serializable {
    String path;

    public VideoInDevice(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
