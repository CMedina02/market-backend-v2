package com.merida.tecnm.market_backend_v2.persistence;

import com.merida.tecnm.market_backend_v2.domain.Product;
import com.merida.tecnm.market_backend_v2.domain.repository.ProductRepository;
import com.merida.tecnm.market_backend_v2.persistence.crud.ProductoCrudRepository;
import com.merida.tecnm.market_backend_v2.persistence.entity.Producto;
import com.merida.tecnm.market_backend_v2.persistence.mapper.ProductMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository implements ProductRepository {

    private final ProductoCrudRepository productoCrudRepository;
    private final ProductMapper mapper;

    public ProductoRepository(ProductoCrudRepository productoCrudRepository, ProductMapper mapper) {
        this.productoCrudRepository = productoCrudRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Product> getAll() {
        List<Producto> productos = (List<Producto>) productoCrudRepository.findAll();
        return mapper.toProducts(productos);
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos = productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
        return Optional.of(mapper.toProducts(productos));
    }

    @Override
    public Optional<Product> getScarceProducts(int quantity) {
        return productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true)
                .flatMap(productos -> productos.stream().findFirst().map(mapper::toProduct));
    }

    @Override
    public Optional<Product> getProduct(int productId) {
        return productoCrudRepository.findById(productId).map(mapper::toProduct);
    }

    @Override
    public Product save(Product product) {
        Producto producto = mapper.toProducto(product);
        return mapper.toProduct(productoCrudRepository.save(producto));
    }

    @Override
    public void delete(int productId) {
        productoCrudRepository.deleteById(productId);
    }
}
