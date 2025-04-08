package com.example.music.entiy;

import java.io.Serializable;

// 实现 Serializable 接口
public class Song implements Serializable {
    private String name;
    private int duration;
    private String lrcPath;

    public Song(String name, int duration, String lrcPath) {
        this.name = name;
        this.duration = duration;
        this.lrcPath = lrcPath;
    }

    // Getters 和 Setters 方法
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