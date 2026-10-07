package com.merida.tecnm.market_backend_v2.persistence.crud;

import com.merida.tecnm.market_backend_v2.persistence.entity.Producto;
import org.springframework.data.repository.CrudRepository;

import java.util.*;

//Metodos abstractos que despues se implementara
public interface ProductoCrudRepository extends CrudRepository<Producto, Integer> {

    List<Producto>findByIdCategoriaOrderByNombreAsc(int idCategoria);


    //CAntidad Stock
    Optional<List<Producto>> findByCantidadStockLessThanAndEstado(int cantidadStock, boolean estado);
}
