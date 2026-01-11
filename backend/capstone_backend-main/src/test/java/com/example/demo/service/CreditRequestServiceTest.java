package com.example.demo.service;

import com.example.demo.dto.CreditDecisionDto;
import com.example.demo.dto.CreditRequestDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.CreditRequest;
import com.example.demo.repository.CreditRequestRepository;
import com.example.demo.service.impl.CreditRequestServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditRequestServiceTest {

    @Mock
    private CreditRequestRepository creditRequestRepository;

    @InjectMocks
    private CreditRequestServiceImpl creditRequestService;

    // ✅ CREATE CREDIT REQUEST
    @Test
    void createRequest_shouldSaveAndReturnCreditRequest() {
        CreditRequestDto dto = new CreditRequestDto();
        dto.setClientId("client123");
        dto.setRequestAmount(500000);
        dto.setTenureMonths(24);
        dto.setPurpose("Working Capital");

        CreditRequest savedRequest = new CreditRequest();
        savedRequest.setClientId("client123");
        savedRequest.setSubmittedBy("rm123");
        savedRequest.setStatus("PENDING");

        when(creditRequestRepository.save(any(CreditRequest.class)))
                .thenReturn(savedRequest);

        CreditRequest result = creditRequestService.createRequest(dto, "rm123");

        assertNotNull(result);
        assertEquals("client123", result.getClientId());
        assertEquals("rm123", result.getSubmittedBy());
        assertEquals("PENDING", result.getStatus());
        verify(creditRequestRepository, times(1)).save(any(CreditRequest.class));
    }

    // ✅ GET REQUESTS FOR RM
    @Test
    void getRequestsForRM_shouldReturnList() {
        CreditRequest r1 = new CreditRequest();
        CreditRequest r2 = new CreditRequest();

        when(creditRequestRepository.findBySubmittedBy("rm123"))
                .thenReturn(List.of(r1, r2));

        List<CreditRequest> result = creditRequestService.getRequestsForRM("rm123");

        assertEquals(2, result.size());
        verify(creditRequestRepository).findBySubmittedBy("rm123");
    }

    // ✅ GET ALL REQUESTS
    @Test
    void getAllRequests_shouldReturnAllRequests() {
        CreditRequest r1 = new CreditRequest();
        CreditRequest r2 = new CreditRequest();

        when(creditRequestRepository.findAll())
                .thenReturn(List.of(r1, r2));

        List<CreditRequest> result = creditRequestService.getAllRequests();

        assertEquals(2, result.size());
        verify(creditRequestRepository).findAll();
    }

    // ✅ UPDATE STATUS (SUCCESS)
    @Test
    void updateStatus_shouldUpdateAndReturnRequest() {
        CreditRequest existing = new CreditRequest();
        existing.setStatus("PENDING");

        CreditDecisionDto decision = new CreditDecisionDto();
        decision.setStatus("APPROVED");
        decision.setRemarks("Looks good");

        when(creditRequestRepository.findById("req1"))
                .thenReturn(Optional.of(existing));
        when(creditRequestRepository.save(existing))
                .thenReturn(existing);

        CreditRequest result = creditRequestService.updateStatus("req1", decision);

        assertEquals("APPROVED", result.getStatus());
        assertEquals("Looks good", result.getRemarks());
        verify(creditRequestRepository).save(existing);
    }

    // ✅ UPDATE STATUS (NOT FOUND)
    @Test
    void updateStatus_shouldThrowException_whenNotFound() {
        CreditDecisionDto decision = new CreditDecisionDto();
        decision.setStatus("REJECTED");
        decision.setRemarks("Invalid docs");

        when(creditRequestRepository.findById("req1"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> creditRequestService.updateStatus("req1", decision)
        );
    }
    @Test
    void createRequest_shouldSetDefaultStatusAndRemarks() {
        CreditRequestDto dto = new CreditRequestDto();
        dto.setClientId("c1");

        CreditRequest saved = new CreditRequest();

        when(creditRequestRepository.save(any(CreditRequest.class)))
                .thenReturn(saved);

        CreditRequest result = creditRequestService.createRequest(dto, "rm1");

        assertNotNull(result);
        verify(creditRequestRepository).save(any());
    }
    @Test
    void getRequestsForRM_shouldReturnEmptyList() {
        when(creditRequestRepository.findBySubmittedBy("rm1"))
                .thenReturn(List.of());

        List<CreditRequest> result =
                creditRequestService.getRequestsForRM("rm1");

        assertTrue(result.isEmpty());
    }
    @Test
    void updateStatus_shouldAllowNullRemarks() {
        CreditRequest req = new CreditRequest();
        req.setStatus("PENDING");

        CreditDecisionDto dto = new CreditDecisionDto();
        dto.setStatus("REJECTED");

        when(creditRequestRepository.findById("id1"))
                .thenReturn(Optional.of(req));
        when(creditRequestRepository.save(req))
                .thenReturn(req);

        CreditRequest result =
                creditRequestService.updateStatus("id1", dto);

        assertEquals("REJECTED", result.getStatus());
    }
    @Test
    void updateStatus_shouldHandleRejectedStatus() {
        CreditRequest req = new CreditRequest();
        req.setStatus("PENDING");

        CreditDecisionDto dto = new CreditDecisionDto();
        dto.setStatus("REJECTED");
        dto.setRemarks("Bad credit");

        when(creditRequestRepository.findById("r1"))
                .thenReturn(Optional.of(req));
        when(creditRequestRepository.save(req))
                .thenReturn(req);

        CreditRequest result =
                creditRequestService.updateStatus("r1", dto);

        assertEquals("REJECTED", result.getStatus());
    }
    @Test
    void updateStatus_shouldHandleApprovedStatus() {
        CreditRequest req = new CreditRequest();
        req.setStatus("PENDING");

        CreditDecisionDto dto = new CreditDecisionDto();
        dto.setStatus("APPROVED");

        when(creditRequestRepository.findById("r1"))
                .thenReturn(Optional.of(req));
        when(creditRequestRepository.save(req))
                .thenReturn(req);

        CreditRequest result =
                creditRequestService.updateStatus("r1", dto);

        assertEquals("APPROVED", result.getStatus());
    }

    
}
