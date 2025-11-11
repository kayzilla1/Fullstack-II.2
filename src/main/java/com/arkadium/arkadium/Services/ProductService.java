package com.arkadium.arkadium.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arkadium.arkadium.Model.Product;
import com.arkadium.arkadium.Repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> findProductsByName(String name){
        return productRepository.buscarPorNombre(name);
    }

    public Product findProductById(long id){
        return productRepository.findById(id).get();
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public void deleteProduct(long id){
        productRepository.deleteById(id);
    }
}