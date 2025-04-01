package com.example.zwx.strategy;

import com.example.zwx.entiy.Song;

import java.util.List;

public class OrderStrategy implements PlayStrategy {
    @Override
    public Song nextSong(List<Song> songs, int currentIndex) {
        int next = (currentIndex + 1) % songs.size();
        return songs.get(next);
    }
}