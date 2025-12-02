package com.arkadium.arkadium.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Schema(description = "Identificador único del producto", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Nombre del producto", example = "Camiseta Deportiva")
    @Column(nullable=false)
    private String nombre;

    @Schema(description = "Precio del producto", example = "2900")
    @Column(nullable=false)
    private int precio;

    @Schema(description = "Stock disponible del producto", example = "100")
    @Column(nullable=false)
    private String stock;

    @Schema(description = "URL de la imagen del producto", example = "http://example.com/imagen.jpg")
    @Column(nullable=false)
    private String url_imagen;

}
