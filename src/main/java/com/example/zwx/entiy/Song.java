package com.example.zwx.entiy;

public class Song {
    private String name;
    private int duration; // 单位：秒
    private String lrcPath;

    public Song(String name, int duration, String lrcPath) {
        this.name = name;
        this.duration = duration;
        this.lrcPath = lrcPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLrcPath() {
        return lrcPath;
    }

    public void setLrcPath(String lrcPath) {
        this.lrcPath = lrcPath;
    }
}