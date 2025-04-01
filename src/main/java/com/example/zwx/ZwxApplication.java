package com.example.zwx;

import com.example.zwx.entiy.User;
import com.example.zwx.service.PlaybackService;
import com.example.zwx.service.PlaylistService;
import com.example.zwx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.scanner.Scanner;

@SpringBootApplication
public class ZwxApplication implements CommandLineRunner {
    @Autowired private UserService userService;
    @Autowired private PlaylistService playlistService;
    @Autowired private PlaybackService playbackService;

    public static void main(String[] args) {
        SpringApplication.run(ZwxApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("欢迎使用音乐播放器！请输入用户名：");
        String username = scanner.nextLine();
        User user = userService.loginOrRegister(username);
        System.out.println("欢迎, " + user.getUsername() + "!");

        while (true) {
            System.out.println("1. 创建歌单\n2. 播放歌单\n3. 切换播放模式\n4. 解析歌词\n5. 分享歌单\n6. 退出");
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
                    playbackService.parseLyrics();
                    break;
                case "5":
                    playlistService.sharePlaylist(user);
                    break;
                case "6":
                    System.out.println("退出播放器");
                    return;
            }
        }
    }
}
