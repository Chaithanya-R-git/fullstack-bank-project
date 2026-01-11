package com.example.demo.model;

import lombok.Data;

@Data   // 🔥 REQUIRED
public class PrimaryContact {

    private String name;
    private String email;
    private String phone;
}
