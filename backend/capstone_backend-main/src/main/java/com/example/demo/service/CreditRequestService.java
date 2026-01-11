package com.example.demo.service;

import com.example.demo.dto.CreditDecisionDto;
import com.example.demo.dto.CreditRequestDto;
import com.example.demo.model.CreditRequest;

import java.util.List;

public interface CreditRequestService {

    CreditRequest createRequest(CreditRequestDto dto, String rmId);

    List<CreditRequest> getRequestsForRM(String rmId);

    List<CreditRequest> getAllRequests();

    CreditRequest updateStatus(String id, CreditDecisionDto dto);
}
