package com.example.zwx.entiy;

import java.util.*;

public class User {
    private String username;
    private List<Playlist> playlists = new ArrayList<>();
    private Map<String, Playlist> sharedUrls = new HashMap<>();

    public String sharePlaylist(Playlist playlist) {
        String url = UUID.randomUUID().toString();
        sharedUrls.put(url, playlist);
        return url;
    }
}