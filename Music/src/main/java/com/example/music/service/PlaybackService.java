package com.example.music.service;

import com.example.music.entiy.User;
import com.example.music.entiy.Playlist;
import com.example.music.entiy.Song;
import com.example.music.strategy.OrderStrategy;
import com.example.music.strategy.PlayStrategy;
import com.example.music.strategy.ShuffleStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

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

    public void togglePlaybackMode() {
        if (currentStrategy instanceof OrderStrategy) {
            currentStrategy = new ShuffleStrategy();
        } else {
            currentStrategy = new OrderStrategy();
        }
        currentPlaylist.setPlayStrategy(currentStrategy);
        System.out.println("切换到 " + (currentStrategy instanceof OrderStrategy ? "顺序播放" : "随机播放"));
    }

    public void playPlaylist(User user) {
        if (user.getPlaylists().isEmpty()) {
            System.out.println("您还没有创建歌单，请先创建歌单。");
            return;
        }
        Playlist playlist = user.getPlaylists().get(0); // 直接播放第一个歌单
        play(playlist);
    }

    public void parseLyrics() {
        if (currentPlaylist != null) {
            Song song = currentPlaylist.getCurrentSong();
            lyricsService.displayLyrics(song.getLrcPath());
        } else {
            System.out.println("当前没有正在播放的歌单，请先播放歌单。");
        }
    }
}