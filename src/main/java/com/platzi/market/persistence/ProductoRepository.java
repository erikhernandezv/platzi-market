package com.platzi.market.persistence;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.repository.ProductRepository;
import com.platzi.market.persistence.crud.ProductoCrudRepository;
import com.platzi.market.persistence.entity.Producto;
import com.platzi.market.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository implements ProductRepository {
    @Autowired
    private ProductoCrudRepository productoCrudRepository;

    @Autowired
    private ProductMapper mapper;

    /**
     * @apiNote Obtenemos la lista de todos los productos
     * @return productos
     */
    @Override
    public List<Product> getAll(){
        List<Producto> productos = (List<Producto>) productoCrudRepository.findAll();
        return mapper.toProducts(productos);
    }

    /**
     * @apiNote Obtenemos los productos pertenecientes a una categoria. Se buscan por el identificador de la categoría
     * @param categoryId
     * @return Product
     */
    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos = productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
        return Optional.of(mapper.toProducts(productos));
    }

    /**
     * @apiNote Obtenemos los productos los cuales su cantidad sea menor a la cantidad en Stock
     * @param quantity
     * @return Product
     */
    @Override
    public Optional<List<Product>> getScarseProducts(int quantity) {
        Optional<List<Producto>> productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true);
        return productos.map(prods -> mapper.toProducts(prods));
    }

    /**
     * @apiNote Obtenemos un producto por su identificador
     * @param productId
     * @return Product
     */
    @Override
    public Optional<Product> getProduct(int productId) {
        return productoCrudRepository.findById(productId).map(prods -> mapper.toProduct(prods));
    }

    /**
     * @apiNote Guardamos productos
     * @param product
     * @return Product
     */
    @Override
    public Product save(Product product) {
        Producto producto = mapper.toProducto(product);
        return mapper.toProduct(productoCrudRepository.save(producto));
    }

    /**
     * @apiNote Permite eliminar productos por su identificador
     * @param productId
     */
    @Override
    public void delete(int productId){
        productoCrudRepository.deleteById(productId);
    }
}
