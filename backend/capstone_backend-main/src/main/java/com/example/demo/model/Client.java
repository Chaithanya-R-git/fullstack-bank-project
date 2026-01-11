package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "clients")
public class Client {

    @Id
    private String id;

    private String companyName;
    private String industry;
    private String address;
    private double annualTurnover;
    private boolean documentsSubmitted;

    private String rmId;
    private PrimaryContact primaryContact;
}
