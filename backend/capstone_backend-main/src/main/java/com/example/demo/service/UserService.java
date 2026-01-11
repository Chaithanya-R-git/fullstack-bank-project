package com.example.demo.service;

import java.util.List;

import com.example.demo.model.User;

public interface UserService {
    User getCurrentUser(String username);
    // 🔥 ADD THIS (Admin use)
    List<User> getAllUsers();
    void updateUserStatus(String userId, boolean active);
}
