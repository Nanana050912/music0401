package com.example.music.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LyricsService {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private List<ScheduledFuture<?>> tasks = new ArrayList<>();

    public void displayLyrics(String lrcPath) {
        List<LyricLine> lines = parseLrc(lrcPath);
        long startTime = System.currentTimeMillis();

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
            e.printStackTrace();
        }
        return lines;
    }

    public void shutdown() {
        tasks.forEach(task -> task.cancel(true));
        scheduler.shutdownNow();
    }
}