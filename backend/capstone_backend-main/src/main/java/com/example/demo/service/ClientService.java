package com.example.demo.service;

import com.example.demo.dto.ClientRequest;
import com.example.demo.model.Client;

import java.util.List;

public interface ClientService {

    Client createClient(ClientRequest request, String rmId);

    List<Client> getMyClients(String rmId);

    Client getClientById(String id);
    

    Client updateClient(String id, ClientRequest request);
}
