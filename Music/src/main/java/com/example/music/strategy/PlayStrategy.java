package com.example.music.strategy;

import com.example.music.entiy.Song;

import java.io.Serializable;
import java.util.List;

public interface PlayStrategy extends Serializable {
    Song nextSong(List<Song> songs, int currentIndex);
}
