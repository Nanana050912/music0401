package com.example.music.controller;

import com.example.music.entity.*;
import com.example.music.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class MusicController {

    @Autowired
    private UserService userService;

    @Autowired
    private SongService songService;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private PlaybackService playbackService;

    private Scanner scanner = new Scanner(System.in);
    private User currentUser;

    /**
     * 启动应用程序
     */
    public void start() {
        welcomeUser();
        showMainMenu();
    }

    /**
     * 欢迎用户并登录
     */
    private void welcomeUser() {
        System.out.println("欢迎使用音乐播放器！");
        System.out.print("请输入您的用户名: ");
        String username = scanner.nextLine();

        currentUser = userService.loginOrRegister(username);
        System.out.println("欢迎, " + currentUser.getUsername() + "!");
    }

    /**
     * 显示主菜单
     */
    private void showMainMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n===== 主菜单 =====");
            System.out.println("1. 创建歌单");
            System.out.println("2. 播放歌单");
            System.out.println("3. 分享歌单");
            System.out.println("4. 上传歌曲");
            System.out.println("5. 退出");
            System.out.print("请选择操作: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createPlaylist();
                    break;
                case "2":
                    playPlaylist();
                    break;
                case "3":
                    sharePlaylist();
                    break;
                case "4":
                    uploadSong();
                    break;
                case "5":
                    exitApplication();
                    running = false;
                    break;
                default:
                    System.out.println("无效的选择，请重新输入。");
            }
        }
    }

    /**
     * 创建歌单
     */
    private void createPlaylist() {
        System.out.print("请输入歌单名称: ");
        String playlistName = scanner.nextLine();

        List<Song> availableSongs = songService.getAllSongs();
        if (availableSongs.isEmpty()) {
            System.out.println("没有可用的歌曲，请先上传歌曲。");
            return;
        }

        System.out.println("\n可用的歌曲列表:");
        for (int i = 0; i < availableSongs.size(); i++) {
            System.out.println((i + 1) + ". " + availableSongs.get(i).getName());
        }

        List<Song> selectedSongs = new ArrayList<>();

        while (true) {
            System.out.print("\n请选择歌曲编号 (输入0完成): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 0) {
                    break;
                }

                if (choice > 0 && choice <= availableSongs.size()) {
                    selectedSongs.add(availableSongs.get(choice - 1));
                    System.out.println("已添加: " + availableSongs.get(choice - 1).getName());
                } else {
                    System.out.println("无效的选择，请重新输入。");
                }
            } catch (NumberFormatException e) {
                System.out.println("请输入有效的数字。");
            }
        }

        if (selectedSongs.isEmpty()) {
            System.out.println("不能创建空歌单。");
            return;
        }

        playlistService.createPlaylist(currentUser, playlistName, selectedSongs);
        System.out.println("歌单 '" + playlistName + "' 创建成功！");
    }

    /**
     * 播放歌单
     */
    private void playPlaylist() {
        List<Playlist> playlists = currentUser.getPlaylists();

        if (playlists.isEmpty()) {
            System.out.println("您还没有创建歌单，请先创建歌单。");
            return;
        }

        System.out.println("\n您的歌单:");
        for (int i = 0; i < playlists.size(); i++) {
            System.out.println((i + 1) + ". " + playlists.get(i).getName());
        }

        System.out.print("请选择要播放的歌单 (输入0取消): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) {
                return;
            }

            if (choice > 0 && choice <= playlists.size()) {
                Playlist selectedPlaylist = playlists.get(choice - 1);
                playbackService.playPlaylist(selectedPlaylist);

                // 播放控制
                boolean controlling = true;
                while (controlling) {
                    System.out.println("\n播放控制: (p) 暂停, (r) 继续播放, (n) 下一首, (m) 切换模式, (q) 返回菜单");
                    String control = scanner.nextLine().toLowerCase();

                    switch (control) {
                        case "p":
                            playbackService.pausePlayback();
                            break;
                        case "r":
                            playbackService.resumePlayback();
                            break;
                        case "n":
                            playbackService.nextSong();
                            break;
                        case "m":
                            playbackService.togglePlaybackMode();
                            break;
                        case "q":
                            controlling = false;
                            break;
                        default:
                            System.out.println("无效的命令。");
                    }
                }
            } else {
                System.out.println("无效的选择。");
            }
        } catch (NumberFormatException e) {
            System.out.println("请输入有效的数字。");
        }
    }


    /**
     * 分享歌单
     */
    private void sharePlaylist() {
        List<Playlist> playlists = currentUser.getPlaylists();

        if (playlists.isEmpty()) {
            System.out.println("您还没有可分享的歌单。");
            return;
        }

        System.out.println("\n您的歌单:");
        for (int i = 0; i < playlists.size(); i++) {
            System.out.println((i + 1) + ". " + playlists.get(i).getName());
        }

        System.out.print("请选择要分享的歌单 (输入0取消): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) {
                return;
            }

            if (choice > 0 && choice <= playlists.size()) {
                Playlist selectedPlaylist = playlists.get(choice - 1);
                String shareUrl = playlistService.sharePlaylist(selectedPlaylist);
                System.out.println("歌单分享成功！分享链接: " + shareUrl);
            } else {
                System.out.println("无效的选择。");
            }
        } catch (NumberFormatException e) {
            System.out.println("请输入有效的数字。");
        }
    }

    /**
     * 上传歌曲
     */
    private void uploadSong() {
        System.out.print("请输入歌曲名称: ");
        String songName = scanner.nextLine();

        System.out.print("请输入歌曲时长 (秒): ");
        int duration;
        try {
            duration = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("无效的时长，请输入数字。");
            return;
        }

        System.out.print("请输入LRC歌词文件路径: ");
        String lrcPath = scanner.nextLine();

        try {
            songService.uploadSong(songName, duration, lrcPath);
            System.out.println("歌曲上传成功！");
        } catch (IOException e) {
            System.out.println("上传歌曲时出错: " + e.getMessage());
        }
    }

    /**
     * 退出应用程序
     */
    private void exitApplication() {
        playbackService.shutdown();
        System.out.println("感谢使用音乐播放器，再见！");
    }
}