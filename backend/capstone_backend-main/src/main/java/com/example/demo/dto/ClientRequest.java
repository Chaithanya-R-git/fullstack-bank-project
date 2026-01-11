package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ClientRequest {

    @NotBlank
    private String companyName;

    @NotBlank
    private String industry;

    @NotBlank
    private String address;

    @NotBlank
    private String contactName;

    @NotBlank
    private String contactEmail;

    @NotBlank
    private String contactPhone;

    @Positive
    private double annualTurnover;

    private boolean documentsSubmitted;
}
