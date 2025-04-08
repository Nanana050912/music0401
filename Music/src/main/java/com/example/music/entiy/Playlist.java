package com.example.music.entiy;

import com.example.music.strategy.PlayStrategy;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

// 实现 Serializable 接口
public class Playlist implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private List<Song> songs;
    private PlayStrategy playStrategy;
    private int currentIndex;

    public Playlist(String name, List<Song> songs, PlayStrategy playStrategy) {
        this.name = name;
        this.songs = songs;
        this.playStrategy = playStrategy;
        this.currentIndex = 0;
    }

    // Getters 和 Setters 方法
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

    public PlayStrategy getPlayStrategy() {
        return playStrategy;
    }

    public void setPlayStrategy(PlayStrategy playStrategy) {
        this.playStrategy = playStrategy;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public Song getCurrentSong() {
        return songs.get(currentIndex);
    }
}