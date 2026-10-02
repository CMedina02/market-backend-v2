package com.merida.tecnm.market_backend_v2.persistence;

import com.merida.tecnm.market_backend_v2.persistence.crud.ProductoCrudRepository;
import com.merida.tecnm.market_backend_v2.persistence.entity.Producto;
import java.util.*;

public class ProductoRepository {

    private ProductoCrudRepository productoCrudRepository;

    //SELECT * FROM productos
    public List<Producto> getAll(){
        //Vamos a "castear"
        return (List<Producto>) productoCrudRepository.findAll();
    }
}
