package com.example.music.service;

import com.example.music.data.UserData;
import com.example.music.entiy.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User loginOrRegister(String username) {
        User user = UserData.getUser(username);
        if (user == null) {
            user = new User(username);
            UserData.addUser(user);
        }
        return user;
    }
}