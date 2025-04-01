package com.example.zwx.entiy;

import com.example.zwx.strategy.PlayStrategy;
import java.util.List;

public class Playlist {
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

    // 切换播放策略方法
    public void switchStrategy(PlayStrategy strategy) {
        this.playStrategy = strategy;
    }
}