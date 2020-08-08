package com.example.dvhplay.myMedia;

import java.io.Serializable;

public class VideoInDevice implements Serializable {
     String path = null;

     String name = null;

    public VideoInDevice(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }
}
