package com.example.zwx.service;

import org.springframework.stereotype.Service;
import com.example.zwx.entiy.User;
import java.util.*;

@Service
public class UserService {
    private Map<String, User> users = new HashMap<>();
    public User loginOrRegister(String username) {
        return users.computeIfAbsent(username, User::new);
    }
}