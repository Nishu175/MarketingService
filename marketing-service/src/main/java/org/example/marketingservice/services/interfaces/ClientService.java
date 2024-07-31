package org.example.marketingservice.services.interfaces;

import org.example.marketingservice.models.Client;

import java.util.List;

public interface ClientService {
    Client findClientById(Long clientId);

    Client saveClient(Client client);
   List<Client> findAllClients();
}
