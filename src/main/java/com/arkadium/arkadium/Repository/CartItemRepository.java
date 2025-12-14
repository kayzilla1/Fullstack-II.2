package com.arkadium.arkadium.Repository;

import com.arkadium.arkadium.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    boolean existsByCartIdAndProductId(Long cartId, Integer productId);
    void deleteByCartIdAndProductId(Long cartId, Integer productId);
}
