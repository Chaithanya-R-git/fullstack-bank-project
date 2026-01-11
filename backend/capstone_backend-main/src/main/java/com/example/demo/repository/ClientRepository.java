package com.example.demo.repository;

import com.example.demo.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClientRepository extends MongoRepository<Client, String> {
    List<Client> findByRmId(String rmId);
}
