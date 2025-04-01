package com.example.zwx.entiy;

import com.example.zwx.strategy.PlayStrategy;

import java.util.List;

public class Playlist {
    private String name;
    private List<Song> songs;
    private PlayStrategy playStrategy;
    private int currentIndex;

    // 切换播放策略方法
    public void switchStrategy(PlayStrategy strategy) {
        this.playStrategy = strategy;
    }
}