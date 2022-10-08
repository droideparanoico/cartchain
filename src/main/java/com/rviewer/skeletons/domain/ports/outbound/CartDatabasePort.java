package com.rviewer.skeletons.domain.ports.outbound;

import com.rviewer.skeletons.domain.model.Cart;

import java.util.Optional;

public interface CartDatabasePort {

    Optional<Cart> get(String id);
    void save(Cart cart);
    void delete(String id);
}
