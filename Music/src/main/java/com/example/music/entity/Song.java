package com.example.music.entity;

import java.io.Serializable;


public class Song implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;        // 歌曲名称
    private int duration;       // 歌曲时长（秒）
    private String lrcPath;     // 歌词文件路径

    public Song(String name, int duration, String lrcPath) {
        this.name = name;
        this.duration = duration;
        this.lrcPath = lrcPath;
    }

    // Getters and Setters
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