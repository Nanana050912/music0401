package com.example.music.entity;

public class LyricLine {
    private long timestamp;
    private String content;

    public LyricLine(long timestamp, String content) {
        this.timestamp = timestamp;
        this.content = content;
    }


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}