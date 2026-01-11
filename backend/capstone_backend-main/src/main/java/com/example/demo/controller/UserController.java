package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/me")
    public User getCurrentUser(Authentication authentication) {
        return userService.getCurrentUser(authentication.getName());
    }

//    @PutMapping("/admin/users/{id}/status")
//    @PreAuthorize("hasRole('ADMIN')")
//    public void updateStatus(@PathVariable String id,
//                             @RequestParam boolean active) {
//        userService.updateUserStatus(id, active);
//    }
}
