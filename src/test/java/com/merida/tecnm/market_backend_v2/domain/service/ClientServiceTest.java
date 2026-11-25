package com.merida.tecnm.market_backend_v2.domain.service;

import com.merida.tecnm.market_backend_v2.domain.Client;
import com.merida.tecnm.market_backend_v2.domain.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClientServiceTest {
    @Test
    void hashesPasswordBeforeSavingAndPreservesHashOnUpdate() {
        ClientRepository repository = mock(ClientRepository.class);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        ClientService service = new ClientService(repository, encoder);
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Client created = new Client();
        created.setPassword("1234");
        service.save(created);
        assertNotEquals("1234", created.getPassword());
        assertTrue(encoder.matches("1234", created.getPassword()));

        when(repository.getClient(1)).thenReturn(Optional.of(created));
        Client updated = new Client();
        service.update(1, updated);
        assertEquals(created.getPassword(), updated.getPassword());
    }
}
