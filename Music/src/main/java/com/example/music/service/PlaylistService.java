package com.example.music.service;

import com.example.music.data.PlaylistData;
import com.example.music.entiy.Playlist;
import com.example.music.entiy.Song;
import com.example.music.entiy.User;
import com.example.music.strategy.OrderStrategy;
import com.example.music.strategy.PlayStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class PlaylistService {

    public void createPlaylist(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入歌单名称：");
        String playlistName = scanner.nextLine();

        List<Song> songs = new ArrayList<>();
        while (true) {
            System.out.println("请输入歌曲名称（输入 '结束' 完成添加）：");
            String songName = scanner.nextLine();
            if ("结束".equals(songName)) {
                break;
            }
            System.out.println("请输入歌曲时长（秒）：");
            int duration = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符
            System.out.println("请输入歌词LRC文件路径：");
            String lrcPath = scanner.nextLine();
            Song song = new Song(songName, duration, lrcPath);
            songs.add(song);
        }

        PlayStrategy playStrategy = new OrderStrategy();
        Playlist playlist = new Playlist(playlistName, songs, playStrategy);
        user.createPlaylist(playlist);
        PlaylistData.addPlaylist(playlist);
        System.out.println("歌单 " + playlistName + " 创建成功！");
    }

    public void sharePlaylist(User user) {
        if (user.getPlaylists().isEmpty()) {
            System.out.println("您还没有创建歌单，请先创建歌单。");
            return;
        }
        System.out.println("请选择要分享的歌单编号：");
        for (int i = 0; i < user.getPlaylists().size(); i++) {
            System.out.println((i + 1) + ". " + user.getPlaylists().get(i).getName());
        }
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if (choice > 0 && choice <= user.getPlaylists().size()) {
            Playlist playlist = user.getPlaylists().get(choice - 1);
            String url = user.sharePlaylist(playlist);
            System.out.println("歌单分享成功，分享链接为：" + url);
        } else {
            System.out.println("无效的选择，请重新输入。");
        }
    }
}