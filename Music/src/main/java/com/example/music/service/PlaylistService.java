package com.example.music.service;

import com.example.music.entity.Playlist;
import com.example.music.entity.Song;
import com.example.music.entity.User;
import com.example.music.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    @Autowired
    private MusicRepository repository;

    /**
     * 创建播放列表
     */
    public void createPlaylist(User user, String name, List<Song> songs) {
        Playlist playlist = new Playlist(name, songs);
        user.addPlaylist(playlist);
        repository.saveUser(user);
    }

    /**
     * 分享播放列表
     */
    public String sharePlaylist(Playlist playlist) {
        // 简单模拟分享功能，返回分享链接
        return "https://example.com/playlist/" + playlist.getName();
    }
}