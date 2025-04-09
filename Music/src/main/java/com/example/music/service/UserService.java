package com.example.music.service;

import com.example.music.entity.User;
import com.example.music.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private MusicRepository repository;

    /**
     * 用户登录或注册
     * 如果用户不存在，创建新用户
     */
    public User loginOrRegister(String username) {
        User user = repository.getUser(username);
        if (user == null) {
            user = new User(username);
            repository.saveUser(user);
        }
        return user;
    }
}