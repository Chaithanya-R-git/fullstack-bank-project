package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreditDecisionDto {

    @NotBlank
    private String status;   // APPROVED / REJECTED

    private String remarks;
}
