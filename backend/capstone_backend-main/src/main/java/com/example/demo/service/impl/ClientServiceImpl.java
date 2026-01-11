package com.example.demo.service.impl;

import com.example.demo.dto.ClientRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Client;
import com.example.demo.model.PrimaryContact;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.ClientService;
import org.springframework.stereotype.Service;
import com.example.demo.kafka.KafkaProducerService;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final KafkaProducerService kafkaProducer;
    public ClientServiceImpl(ClientRepository clientRepository, KafkaProducerService kafkaProducer) {
        this.clientRepository = clientRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public Client createClient(ClientRequest request, String rmId) {

        Client client = new Client();
        client.setCompanyName(request.getCompanyName());
        client.setIndustry(request.getIndustry());
        client.setAddress(request.getAddress());
        client.setAnnualTurnover(request.getAnnualTurnover());
        client.setDocumentsSubmitted(request.isDocumentsSubmitted());
        client.setRmId(rmId);

        PrimaryContact contact = new PrimaryContact();
        contact.setName(request.getContactName());
        contact.setEmail(request.getContactEmail());
        contact.setPhone(request.getContactPhone());

        client.setPrimaryContact(contact);

        Client savedClient = clientRepository.save(client);

     // 🔥 Kafka Event
     kafkaProducer.send("client-created", savedClient);

     return savedClient;

    }

    @Override
    public List<Client> getMyClients(String rmId) {
        return clientRepository.findByRmId(rmId);
    }

    @Override
    public Client getClientById(String id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

    @Override
    public Client updateClient(String id, ClientRequest request) {

        Client client = getClientById(id);

        client.setCompanyName(request.getCompanyName());
        client.setIndustry(request.getIndustry());
        client.setAddress(request.getAddress());
        client.setAnnualTurnover(request.getAnnualTurnover());
        client.setDocumentsSubmitted(request.isDocumentsSubmitted());

        PrimaryContact contact = client.getPrimaryContact();
        if (contact == null) {
            contact = new PrimaryContact();
        }

        contact.setName(request.getContactName());
        contact.setEmail(request.getContactEmail());
        contact.setPhone(request.getContactPhone());

        client.setPrimaryContact(contact);

        return clientRepository.save(client);
    }
}
