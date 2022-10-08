package com.rviewer.skeletons.domain.service;

import com.rviewer.skeletons.domain.model.Cart;
import com.rviewer.skeletons.domain.ports.primary.CartServicePort;
import com.rviewer.skeletons.domain.ports.secondary.DatabasePort;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@Transactional
public class CartService implements CartServicePort {
    private final DatabasePort databasePort;

    public CartService(DatabasePort databasePort) {
        this.databasePort = databasePort;
    }

    @Override
    public Optional<Cart> get(String id) {
        return databasePort.get(id);
    }

    @Override
    public void save(Cart cart) {
        databasePort.save(cart);
    }

    @Override
    public void delete(String id) {
        databasePort.delete(id);
    }
}
