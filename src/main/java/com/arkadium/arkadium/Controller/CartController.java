package com.arkadium.arkadium.Controller;

import com.arkadium.arkadium.Model.Cart;
import com.arkadium.arkadium.Model.User;
import com.arkadium.arkadium.Repository.UserRepository;
import com.arkadium.arkadium.Services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepo;

    private User currentUser(Authentication auth) {
        // auth.getName() = correo (según tu JwtAuthFilter + UserDetails)
        return userRepo.findByCorreo(auth.getName());
    }

    private Map<String, Object> response(Cart cart) {
        List<Map<String, Object>> items = cart.getItems().stream()
            .map(i -> {
                var p = i.getProduct();

                Map<String, Object> map = new HashMap<>();
                map.put("id", p.getId());
                map.put("nombre", p.getNombre());
                map.put("precio", p.getPrecio());
                map.put("url_imagen", p.getUrl_imagen());

                return map;
            })
            .toList();

        return Map.of(
            "cartId", cart.getId(),
            "items", items
        );
    }


    @GetMapping
    public Map<String, Object> get(Authentication auth) {
        User user = currentUser(auth);
        Cart cart = cartService.getOrCreateCart(user);
        return response(cart);
    }

    @PostMapping("/items/{productId}")
    public Map<String, Object> add(Authentication auth, @PathVariable Integer productId) {
        User user = currentUser(auth);
        Cart cart = cartService.addProduct(user, productId);
        return response(cart);
    }

    @DeleteMapping("/items/{productId}")
    public Map<String, Object> remove(Authentication auth, @PathVariable Integer productId) {
        User user = currentUser(auth);
        Cart cart = cartService.removeProduct(user, productId);
        return response(cart);
    }

    @DeleteMapping
    public Map<String, Object> clear(Authentication auth) {
        User user = currentUser(auth);
        Cart cart = cartService.clearCart(user);
        return response(cart);
    }

}
