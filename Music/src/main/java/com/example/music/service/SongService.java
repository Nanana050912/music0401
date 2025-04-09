package com.example.music.service;

import com.example.music.entity.Song;
import com.example.music.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class SongService {

    private static final String RESOURCES_DIR = "src/main/resources/lyrics/";

    @Autowired
    private MusicRepository repository;

    /**
     * 获取所有歌曲
     */
    public List<Song> getAllSongs() {
        return repository.getAllSongs();
    }

    /**
     * 上传歌曲
     */
    public void uploadSong(String songName, int duration, String lrcPath) throws IOException {
        String newLrcPath = copyLrcFile(lrcPath, songName);
        Song song = new Song(songName, duration, newLrcPath);
        repository.addSong(song);
    }

    /**
     * 复制歌词文件到资源目录
     */
    private String copyLrcFile(String originalPath, String songName) throws IOException {
        Path sourcePath = Paths.get(originalPath);
        Path dirPath = Paths.get(RESOURCES_DIR);

        String newFileName = songName + ".lrc";
        Path targetPath = dirPath.resolve(newFileName);
        Files.copy(sourcePath, targetPath);
        return targetPath.toString();
    }
}