package com.merida.tecnm.market_backend_v2.persistence.crud;

import com.merida.tecnm.market_backend_v2.persistence.entity.Cliente;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface ClienteCrudRepository extends CrudRepository<Cliente, String> {
    Optional<Cliente> findByCorreoElectronicoIgnoreCase(String email);
}
