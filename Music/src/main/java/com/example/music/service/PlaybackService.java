package com.example.music.service;

import com.example.music.entity.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;


@Service
public class PlaybackService {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private List<ScheduledFuture<?>> tasks = new ArrayList<>();

    private Playlist currentPlaylist;
    private boolean isPaused = false;
    private long pauseTime = 0;
    private long startTime = 0;

    /**
     * 暂停播放
     */
    public void pausePlayback() {
        if (!isPaused) {
            isPaused = true;
            pauseTime = System.currentTimeMillis();

            // Cancel all scheduled lyric tasks
            tasks.forEach(task -> task.cancel(false));
            tasks.clear();

            System.out.println("⏸ 已暂停播放");
        }
    }

    /**
     * 恢复播放
     */
    public void resumePlayback() {
        if (isPaused) {
            isPaused = false;
            long pauseDuration = System.currentTimeMillis() - pauseTime;

            // Adjust the start time by the pause duration
            startTime += pauseDuration;

            // Reschedule lyrics display
            if (currentPlaylist != null && currentPlaylist.getCurrentSong() != null) {
                displayLyrics(currentPlaylist.getCurrentSong().getLrcPath(), startTime);
            }

            System.out.println("▶ 继续播放");
        }
    }

    /**
     * 播放播放列表
     */
    public void playPlaylist(Playlist playlist) {
        this.currentPlaylist = playlist;
        playCurrentSong();
    }

    /**
     * 播放当前歌曲
     */
    private void playCurrentSong() {
        if (currentPlaylist == null || currentPlaylist.getCurrentSong() == null) {
            System.out.println("没有可播放的歌曲");
            return;
        }

        // Reset pause state
        isPaused = false;

        // Record start time for lyrics synchronization
        startTime = System.currentTimeMillis();

        Song song = currentPlaylist.getCurrentSong();
        System.out.println("▶ 正在播放: " + song.getName());
        displayLyrics(song.getLrcPath(), startTime);
    }

    /**
     * 播放下一首歌曲
     */
    public void nextSong() {
        if (currentPlaylist == null) return;

        List<Song> songs = currentPlaylist.getSongs();
        if (songs.isEmpty()) return;

        int nextIndex;
        if (currentPlaylist.isShuffleMode()) {
            Random random = new Random();
            nextIndex = random.nextInt(songs.size());
        } else {
            nextIndex = (currentPlaylist.getCurrentIndex() + 1) % songs.size();
        }

        currentPlaylist.setCurrentIndex(nextIndex);
        playCurrentSong();
    }

    /**
     * 切换播放模式（顺序/随机）
     */
    public void togglePlaybackMode() {
        if (currentPlaylist == null) return;

        boolean newMode = !currentPlaylist.isShuffleMode();
        currentPlaylist.setShuffleMode(newMode);
        System.out.println("已切换到" + (newMode ? "随机播放" : "顺序播放") + "模式");
    }

    /**
     * 修改后的显示歌词方法，接受起始时间参数
     */
    public void displayLyrics(String lrcPath, long startTimeParam) {
        List<LyricLine> lines = parseLrc(lrcPath);
        if (lines.isEmpty()) return;

        // Store the start time
        this.startTime = startTimeParam;

        // 取消之前的任务
        tasks.forEach(task -> task.cancel(false));
        tasks.clear();

        for (LyricLine line : lines) {
            long delay = line.getTimestamp() - (System.currentTimeMillis() - startTime);
            if (delay > 0) {
                tasks.add(scheduler.schedule(
                        () -> System.out.println(line.getContent()),
                        delay,
                        TimeUnit.MILLISECONDS
                ));
            }
        }
    }

    /**
     * 显示歌词 (保留此方法以兼容旧代码)
     */
    public void displayLyrics(String lrcPath) {
        displayLyrics(lrcPath, System.currentTimeMillis());
    }

    /**
     * 解析LRC歌词文件
     */
    private List<LyricLine> parseLrc(String path) {
        List<LyricLine> lines = new ArrayList<>();
        File file = new File(path);

        if (!file.exists()) {
            System.err.println("文件不存在: " + path);
            return lines;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            Pattern pattern = Pattern.compile("\\[(\\d{2}):(\\d{2})\\.(\\d{2})\\](.*)");

            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    int minutes = Integer.parseInt(matcher.group(1));
                    int seconds = Integer.parseInt(matcher.group(2));
                    int millis = Integer.parseInt(matcher.group(3));
                    long timestamp = (minutes * 60 + seconds) * 1000 + millis * 10;
                    String content = matcher.group(4);
                    lines.add(new LyricLine(timestamp, content));
                }
            }
        } catch (IOException e) {
            System.err.println("读取文件时出错: " + path);
        }

        return lines;
    }

    /**
     * 关闭播放服务
     */
    public void shutdown() {
        tasks.forEach(task -> task.cancel(true));
        scheduler.shutdownNow();
    }
}