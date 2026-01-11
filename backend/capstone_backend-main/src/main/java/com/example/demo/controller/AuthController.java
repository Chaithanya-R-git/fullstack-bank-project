package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        System.out.println("🔥 LOGIN CONTROLLER HIT 🔥");
        return authService.login(request);
    }


    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public void register(@RequestBody RegisterRequest request) {
        authService.register(request);
    }
}
