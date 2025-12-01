package com.arkadium.arkadium.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.arkadium.arkadium.Model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
    @Query("select p from Product p where lower(p.nombre) = lower(:nom)")
    List<Product> buscarPorNombre(@Param("nom") String nombre);
}
