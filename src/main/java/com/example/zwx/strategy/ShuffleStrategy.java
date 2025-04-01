package com.example.zwx.strategy;

import com.example.zwx.entiy.Song;

import java.util.List;
import java.util.Random;

public class ShuffleStrategy implements com.example.zwx.strategy.PlayStrategy {
    @Override
    public Song nextSong(List<Song> songs, int currentIndex) {
        return songs.get(new Random().nextInt(songs.size()));
    }
}