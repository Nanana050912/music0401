package com.example.music.entity;

import java.io.Serializable;
import java.util.List;

public class Playlist implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;            // 播放列表名称
    private List<Song> songs;       // 歌曲列表
    private boolean shuffleMode;    // 是否随机播放
    private int currentIndex;       // 当前播放索引

    public Playlist(String name, List<Song> songs) {
        this.name = name;
        this.songs = songs;
        this.shuffleMode = false;   // 默认为顺序播放
        this.currentIndex = 0;      // 默认从第一首开始
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public boolean isShuffleMode() {
        return shuffleMode;
    }

    public void setShuffleMode(boolean shuffleMode) {
        this.shuffleMode = shuffleMode;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    /**
     * 获取当前播放的歌曲
     */
    public Song getCurrentSong() {
        if (songs.isEmpty()) {
            return null;
        }
        return songs.get(currentIndex);
    }
}