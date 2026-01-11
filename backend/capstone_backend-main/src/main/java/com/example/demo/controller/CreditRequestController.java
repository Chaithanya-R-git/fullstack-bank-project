package com.example.demo.controller;

import com.example.demo.dto.CreditDecisionDto;
import com.example.demo.dto.CreditRequestDto;
import com.example.demo.model.CreditRequest;
import com.example.demo.service.CreditRequestService;
import com.example.demo.config.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit-requests")
@CrossOrigin(origins = "http://localhost:4200")
public class CreditRequestController {

    private final CreditRequestService service;
    private final JwtUtil jwtUtil;

    public CreditRequestController(CreditRequestService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    // RM creates request
    @PostMapping
    @PreAuthorize("hasRole('RM')")
    public CreditRequest create(@Valid @RequestBody CreditRequestDto dto,
                                HttpServletRequest http) {

        String token = http.getHeader("Authorization").substring(7);
        String rmId = jwtUtil.extractUsername(token);

        return service.createRequest(dto, rmId);
    }

    // RM → own requests
    @GetMapping
    @PreAuthorize("hasRole('RM')")
    public List<CreditRequest> getMyRequests(HttpServletRequest http) {

        String token = http.getHeader("Authorization").substring(7);
        String rmId = jwtUtil.extractUsername(token);

        return service.getRequestsForRM(rmId);
    }

    // Analyst → all requests
    @GetMapping("/all")
    @PreAuthorize("hasRole('ANALYST')")
    public List<CreditRequest> getAll() {
        return service.getAllRequests();
    }

    // Analyst updates decision
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ANALYST')")
    public CreditRequest update(@PathVariable String id,
                                @Valid @RequestBody CreditDecisionDto dto) {
        return service.updateStatus(id, dto);
    }
}
