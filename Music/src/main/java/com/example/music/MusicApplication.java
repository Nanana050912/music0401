package com.example.music;

import com.example.music.controller.MusicController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MusicApplication {

    public static void main(String[] args) {
        // 启动Spring应用
        ApplicationContext context = SpringApplication.run(MusicApplication.class, args);

        // 获取控制器并启动应用
        MusicController controller = context.getBean(MusicController.class);
        controller.start();
    }
}