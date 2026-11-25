package com.merida.tecnm.market_backend_v2.domain.service;

import com.merida.tecnm.market_backend_v2.domain.Client;
import com.merida.tecnm.market_backend_v2.domain.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MarketUserDetailsService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> clientOpt = clientRepository.getClient(username);
        if (clientOpt.isEmpty()) {
            clientOpt = clientRepository.getByEmail(username);
        }

        Client client = clientOpt.orElseThrow(() ->
                new UsernameNotFoundException("Usuario no encontrado con identificador: " + username)
        );

        String password = client.getPassword();
        if (password == null || password.isBlank()) {
            throw new UsernameNotFoundException("El usuario no tiene una contraseña asignada.");
        }

        return new User(client.getClientId(), password, new ArrayList<>());
    }
}
