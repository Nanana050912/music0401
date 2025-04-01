package com.example.zwx.entiy;

import java.util.*;

public class User {
    private String username;
    private List<Playlist> playlists = new ArrayList<>();
    private Map<String, Playlist> sharedUrls = new HashMap<>();

    public User(String username) {
        this.username = username;
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

    public Map<String, Playlist> getSharedUrls() {
        return sharedUrls;
    }

    public void setSharedUrls(Map<String, Playlist> sharedUrls) {
        this.sharedUrls = sharedUrls;
    }

    public String sharePlaylist(Playlist playlist) {
        String url = UUID.randomUUID().toString();
        sharedUrls.put(url, playlist);
        return url;
    }

    public void createPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }
}