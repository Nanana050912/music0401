package com.example.zwx.service;

import com.example.zwx.entiy.User;
import com.example.zwx.entiy.Playlist;
import com.example.zwx.entiy.Song;
import com.example.zwx.strategy.OrderStrategy;
import com.example.zwx.strategy.PlayStrategy;
import com.example.zwx.strategy.ShuffleStrategy;
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
        System.out.println("请选择要播放的歌单编号：");
        for (int i = 0; i < user.getPlaylists().size(); i++) {
            System.out.println((i + 1) + ". " + user.getPlaylists().get(i).getName());
        }
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if (choice > 0 && choice <= user.getPlaylists().size()) {
            Playlist playlist = user.getPlaylists().get(choice - 1);
            play(playlist);
        } else {
            System.out.println("无效的选择，请重新输入。");
        }
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