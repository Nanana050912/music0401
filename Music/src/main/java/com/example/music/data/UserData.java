package com.example.music.data;

import com.example.music.entiy.User;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserData {
    private static final String USER_DATA_FILE = "user_data.ser";
    private static Map<String, User> users = new HashMap<>();

    static {
        loadUserData();
    }

    public static User getUser(String username) {
        return users.get(username);
    }

    public static void addUser(User user) {
        users.put(user.getUsername(), user);
        saveUserData();
    }

    private static void loadUserData() {
        File file = new File(USER_DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                users = (Map<String, User>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void saveUserData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_DATA_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}