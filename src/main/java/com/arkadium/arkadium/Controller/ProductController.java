package com.arkadium.arkadium.Controller;

import com.arkadium.arkadium.Model.Product;
import com.arkadium.arkadium.Repository.ProductRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
@Tag(name = "Product Controller", description = "Operaciones CRUD para productos")
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    @Operation(summary = "Obtener todos los productos", description = "Devuelve una lista de todos los productos disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente")
    @GetMapping
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    @Operation(summary = "Obtener un producto por ID", description = "Devuelve los detalles de un producto específico dado su ID")
    @ApiResponse(responseCode = "200", description = "Producto obtenido exitosamente")
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Operation(summary = "Crear un nuevo producto", description = "Agrega un nuevo producto a la base de datos")
    @ApiResponse(responseCode = "201", description = "Producto creado exitosamente")
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return repo.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        repo.deleteById(id);
    }
}
