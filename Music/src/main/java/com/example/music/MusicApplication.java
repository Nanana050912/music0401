package com.example.music;

import com.example.music.entiy.User;
import com.example.music.service.PlaybackService;
import com.example.music.service.PlaylistService;
import com.example.music.service.UserService;
import com.example.music.service.LyricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class MusicApplication implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private PlaybackService playbackService;
    @Autowired
    private LyricsService lyricsService;

    public static void main(String[] args) {
        SpringApplication.run(MusicApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("欢迎使用音乐播放器！请输入用户名：");
        String username = scanner.nextLine();
        User user = userService.loginOrRegister(username);
        System.out.println("欢迎, " + user.getUsername() + "!");

        while (true) {
            System.out.println("1. 创建歌单\n2. 播放歌单\n3. 切换播放模式\n4. 分享歌单\n5. 退出");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    playlistService.createPlaylist(user);
                    break;
                case "2":
                    playbackService.playPlaylist(user);
                    break;
                case "3":
                    playbackService.togglePlaybackMode();
                    break;
                case "4":
                    playlistService.sharePlaylist(user);
                    break;
                case "5":
                    lyricsService.shutdown();
                    System.out.println("退出播放器");
                    return;
                default:
                    System.out.println("无效的选择，请重新输入。");
            }
        }
    }
}