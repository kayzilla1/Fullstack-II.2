package com.arkadium.arkadium.Services;

import com.arkadium.arkadium.Model.*;
import com.arkadium.arkadium.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepo;
    private final CartItemRepository itemRepo;
    private final ProductRepository productRepo;

    @Transactional
    public Cart getOrCreateCart(User user) {
        return cartRepo.findByUserId(user.getId()).orElseGet(() -> {
            Cart c = new Cart();
            c.setUser(user);
            return cartRepo.save(c);
        });
    }

    @Transactional
    public Cart addProduct(User user, Integer productId) {
        Cart cart = getOrCreateCart(user);

        // No dejar duplicados (1 unidad por juego)
        if (itemRepo.existsByCartIdAndProductId(cart.getId(), productId)) {
            return cart;
        }

        Product p = productRepo.findById(productId).orElseThrow();

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProduct(p);
        itemRepo.save(item);

        return getOrCreateCart(user);
    }

    @Transactional
    public Cart removeProduct(User user, Integer productId) {
        Cart cart = getOrCreateCart(user);
        itemRepo.deleteByCartIdAndProductId(cart.getId(), productId);
        return getOrCreateCart(user);
    }

    @Transactional
    public Cart checkout(User user) {
        Cart cart = getOrCreateCart(user);
        // Validar stock
        for (CartItem item : cart.getItems()) {
            Product p = item.getProduct();
            if (p.getStock() <= 0) {
                throw new IllegalStateException(
                    "Sin stock para el producto: " + p.getNombre()
                );
            }
        }
        // Descontar stock
        for (CartItem item : cart.getItems()) {
            Product p = item.getProduct();
            p.setStock(p.getStock() - 1);
            productRepo.save(p);
        }
        itemRepo.deleteByCartId(cart.getId());
        return getOrCreateCart(user);
    }

    public long total(Cart cart) {
        return cart.getItems().stream().mapToLong(i -> i.getProduct().getPrecio()).sum();
    }
    
}
