package org.example.marketingservice.services.impl;

import org.example.marketingservice.exceptions.InvalidRequest;
import org.example.marketingservice.models.Client;
import org.example.marketingservice.repositories.ClientRepository;
import org.example.marketingservice.services.interfaces.ClientService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client findClientById(Long clientId) {
        return clientRepository.findById(clientId).orElse(null);
    }

    @Override
    public Client saveClient(Client client) {
        if (Objects.isNull(client)) {
            throw new InvalidRequest("Invalid client details");
        }
        return clientRepository.save(client);
    }

    @Override
    public List<Client> findAllClients() {
        List<Client> clients = new ArrayList<>();
        for (Client client :clientRepository.findAll()) {
            clients.add(client);
        }
        return clients;
    }
}
