package org.example.marketingservice.controllers;

import org.example.marketingservice.models.Client;
import org.example.marketingservice.services.interfaces.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public Client createClient(@RequestBody(required = true) Client client) {
        return clientService.saveClient(client);
    }
    @GetMapping
    public List<Client> getAllClients() {
        return clientService.findAllClients();
    }

    @GetMapping("/{clientId}")
    public Client getClientById(@PathVariable Long clientId) {
        return clientService.findClientById(clientId);
    }


}
