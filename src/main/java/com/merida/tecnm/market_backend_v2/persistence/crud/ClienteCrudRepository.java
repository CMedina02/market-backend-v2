package com.merida.tecnm.market_backend_v2.persistence.crud;

import com.merida.tecnm.market_backend_v2.persistence.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteCrudRepository extends CrudRepository<Cliente, Integer> {
}
