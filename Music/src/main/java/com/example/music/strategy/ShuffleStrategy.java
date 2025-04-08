package com.example.music.strategy;

import com.example.music.entiy.Song;
import java.util.List;
import java.util.Random;

public class ShuffleStrategy implements PlayStrategy {
    @Override
    public Song nextSong(List<Song> songs, int currentIndex) {
        return songs.get(new Random().nextInt(songs.size()));
    }
}