package com.merida.tecnm.market_backend_v2.domain.service;

import com.merida.tecnm.market_backend_v2.domain.Client;
import com.merida.tecnm.market_backend_v2.domain.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAll() {
        return clientRepository.getAll();
    }

    public Optional<Client> getClient(int clientId) {
        return clientRepository.getClient(clientId);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> update(int clientId, Client client) {
        return clientRepository.getClient(clientId).map(existing -> {
            client.setClientId(clientId);
            return clientRepository.save(client);
        });
    }

    public boolean delete(int clientId) {
        if (getClient(clientId).isPresent()) {
            clientRepository.delete(clientId);
            return true;
        }
        return false;
    }
}
