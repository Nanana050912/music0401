package com.example.music.entiy;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// 实现 Serializable 接口
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String username;
    private List<Playlist> playlists;

    public User(String username) {
        this.username = username;
        this.playlists = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void createPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    public String sharePlaylist(Playlist playlist) {
        // 简单模拟分享，返回一个分享链接
        return "https://example.com/playlist/" + playlist.getName();
    }
}