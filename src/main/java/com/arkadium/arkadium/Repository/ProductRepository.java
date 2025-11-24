package com.arkadium.arkadium.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.arkadium.arkadium.Model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
    @Query("select p from Product p where lower(p.nombre) = lower(:nom)")
    List<Product> buscarPorNombre(@Param("nom") String nombre);
}
