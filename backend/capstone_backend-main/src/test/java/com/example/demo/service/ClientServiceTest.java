package com.example.demo.service;

import com.example.demo.dto.ClientRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Client;
import com.example.demo.model.PrimaryContact;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.impl.ClientServiceImpl;
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
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    // ✅ CREATE CLIENT
    @Test
    void createClient_shouldSaveAndReturnClient() {
        ClientRequest request = new ClientRequest();
        request.setCompanyName("ABC Pvt Ltd");
        request.setIndustry("IT");
        request.setAddress("Bangalore");
        request.setAnnualTurnover(1000000);
        request.setDocumentsSubmitted(true);
        request.setContactName("John");
        request.setContactEmail("john@abc.com");
        request.setContactPhone("9876543210");

        Client savedClient = new Client();
        savedClient.setCompanyName("ABC Pvt Ltd");
        savedClient.setRmId("rm123");

        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);

        Client result = clientService.createClient(request, "rm123");

        assertNotNull(result);
        assertEquals("ABC Pvt Ltd", result.getCompanyName());
        assertEquals("rm123", result.getRmId());
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    // ✅ GET MY CLIENTS
    @Test
    void getMyClients_shouldReturnClientList() {
        Client c1 = new Client();
        Client c2 = new Client();

        when(clientRepository.findByRmId("rm123"))
                .thenReturn(List.of(c1, c2));

        List<Client> result = clientService.getMyClients("rm123");

        assertEquals(2, result.size());
        verify(clientRepository).findByRmId("rm123");
    }

    // ✅ GET CLIENT BY ID (SUCCESS)
    @Test
    void getClientById_shouldReturnClient() {
        Client client = new Client();
        client.setCompanyName("ABC Pvt Ltd");

        when(clientRepository.findById("client1"))
                .thenReturn(Optional.of(client));

        Client result = clientService.getClientById("client1");

        assertNotNull(result);
        assertEquals("ABC Pvt Ltd", result.getCompanyName());
    }

    // ✅ GET CLIENT BY ID (NOT FOUND)
    @Test
    void getClientById_shouldThrowException_whenNotFound() {
        when(clientRepository.findById("client1"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> clientService.getClientById("client1")
        );
    }

    // ✅ UPDATE CLIENT
    @Test
    void updateClient_shouldUpdateAndReturnClient() {
        Client existingClient = new Client();
        existingClient.setCompanyName("Old Name");

        PrimaryContact contact = new PrimaryContact();
        existingClient.setPrimaryContact(contact);

        ClientRequest request = new ClientRequest();
        request.setCompanyName("New Name");
        request.setIndustry("Finance");
        request.setAddress("Mumbai");
        request.setAnnualTurnover(2000000);
        request.setDocumentsSubmitted(true);
        request.setContactName("Rahul");
        request.setContactEmail("rahul@test.com");
        request.setContactPhone("9999999999");

        when(clientRepository.findById("client1"))
                .thenReturn(Optional.of(existingClient));
        when(clientRepository.save(any(Client.class)))
                .thenReturn(existingClient);

        Client result = clientService.updateClient("client1", request);

        assertEquals("New Name", result.getCompanyName());
        assertEquals("Rahul", result.getPrimaryContact().getName());
        verify(clientRepository).save(existingClient);
    }
    @Test
    void createClient_shouldHandleMissingContactDetails() {
        ClientRequest request = new ClientRequest();
        request.setCompanyName("Test Co");
        request.setIndustry("IT");

        Client savedClient = new Client();
        savedClient.setCompanyName("Test Co");

        when(clientRepository.save(any(Client.class)))
                .thenReturn(savedClient);

        Client result = clientService.createClient(request, "rm1");

        assertNotNull(result);
        verify(clientRepository).save(any(Client.class));
    }
    @Test
    void getMyClients_shouldReturnEmptyList() {
        when(clientRepository.findByRmId("rm123"))
                .thenReturn(List.of());

        List<Client> result = clientService.getMyClients("rm123");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    @Test
    void updateClient_shouldCreatePrimaryContact_ifNull() {
        Client existingClient = new Client();
        existingClient.setCompanyName("Old");

        ClientRequest request = new ClientRequest();
        request.setCompanyName("Updated");
        request.setContactName("New Contact");

        when(clientRepository.findById("id1"))
                .thenReturn(Optional.of(existingClient));
        when(clientRepository.save(any(Client.class)))
                .thenReturn(existingClient);

        Client result = clientService.updateClient("id1", request);

        assertNotNull(result.getPrimaryContact());
        assertEquals("New Contact", result.getPrimaryContact().getName());
    }

    @Test
    void updateClient_shouldThrowException_whenClientMissing() {
        when(clientRepository.findById("id1"))
                .thenReturn(Optional.empty());

        assertThrows(
            ResourceNotFoundException.class,
            () -> clientService.updateClient("id1", new ClientRequest())
        );
    }
    @Test
    void updateClient_shouldHandleDocumentsNotSubmitted() {
        Client client = new Client();
        client.setDocumentsSubmitted(true);

        ClientRequest request = new ClientRequest();
        request.setDocumentsSubmitted(false);

        when(clientRepository.findById("c1"))
                .thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class)))
                .thenReturn(client);

        Client result = clientService.updateClient("c1", request);

        assertFalse(result.isDocumentsSubmitted());
    }
    @Test
    void updateClient_shouldUpdateExistingPrimaryContact() {
        PrimaryContact contact = new PrimaryContact();
        contact.setName("Old");

        Client client = new Client();
        client.setPrimaryContact(contact);

        ClientRequest request = new ClientRequest();
        request.setContactName("New");

        when(clientRepository.findById("c1"))
                .thenReturn(Optional.of(client));
        when(clientRepository.save(client))
                .thenReturn(client);

        Client result = clientService.updateClient("c1", request);

        assertEquals("New", result.getPrimaryContact().getName());
    }



}
