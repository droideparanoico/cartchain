package com.rviewer.skeletons.domain.ports.primary;

import com.rviewer.skeletons.domain.model.Cart;

import java.util.Optional;

public interface CartServicePort {
    Optional<Cart> get(String id);
    void save(Cart cart);
    void delete(String id);
}
