package com.example.demo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "creditRequests")
public class CreditRequest {
    @Id
    private String id;
    private String clientId;
    private String submittedBy;
    private double requestAmount;
    private int tenureMonths;
    private String purpose;
    private String status ;
    private String remarks = "";
    private Instant createdAt = Instant.now();
}
