package com.example.demo.repository;

import com.example.demo.model.CreditRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CreditRequestRepository extends MongoRepository<CreditRequest, String> {
    List<CreditRequest> findBySubmittedBy(String rmId);
}
