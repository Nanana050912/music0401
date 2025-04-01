package com.example.zwx.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

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
        // 解析LRC文件实现
    }
}