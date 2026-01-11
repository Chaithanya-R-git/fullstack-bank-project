package com.example.demo.controller;

import com.example.demo.dto.ClientRequest;
import com.example.demo.model.Client;
import com.example.demo.service.ClientService;
import com.example.demo.config.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/rm")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {

    private final ClientService clientService;
    private final JwtUtil jwtUtil;

    public ClientController(ClientService clientService, JwtUtil jwtUtil) {
        this.clientService = clientService;
        this.jwtUtil = jwtUtil;
    }

    @PreAuthorize("hasRole('RM')")
    @PostMapping("/clients")
    public Client createClient(@Valid @RequestBody ClientRequest request,
                               HttpServletRequest http) {

        String token = http.getHeader("Authorization").substring(7);
        String rmId = jwtUtil.extractUsername(token);

        return clientService.createClient(request, rmId);
    }

    @PreAuthorize("hasAuthority('RM')")
    @GetMapping("/clients")
    public List<Client> getMyClients(HttpServletRequest http) {

        String token = http.getHeader("Authorization").substring(7);
        String rmId = jwtUtil.extractUsername(token);

        return clientService.getMyClients(rmId);
    }

    @GetMapping("/clients/{id}")
    public Client getClient(@PathVariable String id) {
        return clientService.getClientById(id);
    }

    @PutMapping("/clients/{id}")
    public Client updateClient(@PathVariable String id,
                               @Valid @RequestBody ClientRequest request) {
        return clientService.updateClient(id, request);
    }
}
