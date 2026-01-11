package com.example.demo.service.impl;

import com.example.demo.dto.CreditDecisionDto;
import com.example.demo.dto.CreditRequestDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.kafka.KafkaProducerService;
import com.example.demo.model.CreditRequest;
import com.example.demo.repository.CreditRequestRepository;
import com.example.demo.service.CreditRequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreditRequestServiceImpl implements CreditRequestService {

    private final CreditRequestRepository repository;
    private final KafkaProducerService kafkaProducer;

    public CreditRequestServiceImpl(CreditRequestRepository repository, KafkaProducerService kafkaProducer) {
        this.repository = repository;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public CreditRequest createRequest(CreditRequestDto dto, String rmId) {

        CreditRequest request = new CreditRequest();
        request.setClientId(dto.getClientId());
        request.setSubmittedBy(rmId);
        request.setRequestAmount(dto.getRequestAmount());
        request.setTenureMonths(dto.getTenureMonths());
        request.setPurpose(dto.getPurpose());
        request.setStatus("PENDING");
        request.setRemarks("");
        

        CreditRequest saved = repository.save(request);

     // 🔥 Kafka Event
     kafkaProducer.send("credit-request-created", saved);

     return saved;

    }

    @Override
    public List<CreditRequest> getRequestsForRM(String rmId) {
        return repository.findBySubmittedBy(rmId);
    }

    @Override
    public List<CreditRequest> getAllRequests() {
        return repository.findAll();
    }

    @Override
    public CreditRequest updateStatus(String id, CreditDecisionDto dto) {

        CreditRequest request = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Credit request not found"));

        request.setStatus(dto.getStatus());
        request.setRemarks(dto.getRemarks());

        CreditRequest updated = repository.save(request);

     // 🔥 Kafka Event
     kafkaProducer.send("credit-decision-updated", updated);

     return updated;

    }
}
