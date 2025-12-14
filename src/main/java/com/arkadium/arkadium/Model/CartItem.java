package com.arkadium.arkadium.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "carrito_items",
    uniqueConstraints = @UniqueConstraint(columnNames = {"carrito_id", "producto_id"}) // evita duplicados
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "carrito_id", nullable = false)
    private Cart cart;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id", nullable = false)
    private Product product;
}
