package com.merida.tecnm.market_backend_v2.domain.repository;

import com.merida.tecnm.market_backend_v2.domain.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {

    List<Client> getAll();
    Optional<Client> getClient(int clientId);
    Client save(Client client);
    void delete(int clientId);
}
