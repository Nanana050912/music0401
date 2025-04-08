package com.example.music.data;

import com.example.music.entiy.Playlist;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PlaylistData {
    private static final String PLAYLIST_DATA_FILE = "playlist_data.ser";
    private static Map<String, Playlist> playlists = new HashMap<>();

    static {
        loadPlaylistData();
    }

    public static Playlist getPlaylist(String playlistName) {
        return playlists.get(playlistName);
    }

    public static void addPlaylist(Playlist playlist) {
        playlists.put(playlist.getName(), playlist);
        savePlaylistData();
    }

    private static void loadPlaylistData() {
        File file = new File(PLAYLIST_DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                playlists = (Map<String, Playlist>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void savePlaylistData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PLAYLIST_DATA_FILE))) {
            oos.writeObject(playlists);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}