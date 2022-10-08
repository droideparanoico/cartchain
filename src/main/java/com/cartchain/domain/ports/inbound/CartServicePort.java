package com.cartchain.domain.ports.inbound;

import com.cartchain.domain.model.Cart;

import java.util.Optional;

public interface CartServicePort {
    Optional<Cart> get(String id);
    void save(Cart cart);
    void delete(String id);
}
