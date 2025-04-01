package com.example.zwx.strategy;

import com.example.zwx.entiy.Song;

import java.util.List;

public interface PlayStrategy {
    Song nextSong(List<Song> songs, int currentIndex);
}
