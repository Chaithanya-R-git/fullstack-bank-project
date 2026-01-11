package com.example.demo.kafka;

import com.example.demo.model.CreditRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(
        topics = "credit-request-created",
        groupId = "analyst-group"
    )
    public void listenCreditRequests(CreditRequest request) {
        System.out.println("📩 New Credit Request: " + request.getId());
    }

    @KafkaListener(
        topics = "credit-decision-updated",
        groupId = "rm-group"
    )
    public void listenDecision(CreditRequest request) {
        System.out.println("✅ Credit Decision: " + request.getStatus());
    }
}
