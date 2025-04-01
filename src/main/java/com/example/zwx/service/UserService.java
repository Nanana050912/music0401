package com.example.zwx.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private Map<String, User> users = new HashMap<>();
    public User loginOrRegister(String username) {
        return users.computeIfAbsent(username, User::new);
    }
}