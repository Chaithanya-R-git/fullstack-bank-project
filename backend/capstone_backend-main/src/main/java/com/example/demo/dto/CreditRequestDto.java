package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreditRequestDto {

    @NotBlank
    private String clientId;

    @Positive
    private double requestAmount;

    @Positive
    private int tenureMonths;

    @NotBlank
    private String purpose;
}
