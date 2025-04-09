package com.example.music.repository;

import com.example.music.entity.*;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
public class MusicRepository {
    private static final String USER_DATA_FILE = "user_data.ser";
    private static final String SONG_DATA_FILE = "song_data.ser";

    private Map<String, User> users = new HashMap<>();
    private List<Song> songs = new ArrayList<>();

    /**
     * 构造函数，初始化并加载数据
     */
    public MusicRepository() {
        loadUserData();
        loadSongData();
    }

    /**
     * 获取用户信息
     */
    public User getUser(String username) {
        return users.get(username);
    }

    /**
     * 保存用户信息
     */
    public void saveUser(User user) {
        users.put(user.getUsername(), user);
        saveUserData();
    }

    /**
     * 获取所有歌曲
     */
    public List<Song> getAllSongs() {
        return songs;
    }

    /**
     * 添加歌曲
     */
    public void addSong(Song song) {
        songs.add(song);
        saveSongData();
    }

    /**
     * 加载用户数据
     */
    private void loadUserData() {
        File file = new File(USER_DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                users = (Map<String, User>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("加载用户数据时出错: " + e.getMessage());
            }
        }
    }

    /**
     * 保存用户数据
     */
    private void saveUserData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_DATA_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.err.println("保存用户数据时出错: " + e.getMessage());
        }
    }

    /**
     * 加载歌曲数据
     */
    private void loadSongData() {
        File file = new File(SONG_DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                songs = (List<Song>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("加载歌曲数据时出错: " + e.getMessage());
            }
        }
    }

    /**
     * 保存歌曲数据
     */
    private void saveSongData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SONG_DATA_FILE))) {
            oos.writeObject(songs);
        } catch (IOException e) {
            System.err.println("保存歌曲数据时出错: " + e.getMessage());
        }
    }
}