package com.example.zwx.service;

import com.example.zwx.entiy.Playlist;
import com.example.zwx.entiy.Song;
import com.example.zwx.strategy.PlayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaybackService {
    @Autowired
    private LyricsService lyricsService;

    private Playlist currentPlaylist;
    private PlayStrategy currentStrategy;

    public void play(Playlist playlist) {
        this.currentPlaylist = playlist;
        this.currentStrategy = playlist.getPlayStrategy();
        playCurrentSong();
    }

    private void playCurrentSong() {
        Song song = currentPlaylist.getCurrentSong();
        System.out.println("▶ Playing: " + song.getName());
        lyricsService.displayLyrics(song.getLrcPath());
    }

    public void next() {
        Song nextSong = currentStrategy.nextSong(
                currentPlaylist.getSongs(),
                currentPlaylist.getCurrentIndex()
        );
        currentPlaylist.setCurrentIndex(
                currentPlaylist.getSongs().indexOf(nextSong)
        );
        playCurrentSong();
    }
}