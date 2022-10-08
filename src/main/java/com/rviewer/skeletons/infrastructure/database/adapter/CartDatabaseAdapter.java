package com.rviewer.skeletons.infrastructure.database.adapter;

import com.rviewer.skeletons.domain.model.Cart;
import com.rviewer.skeletons.domain.ports.secondary.CartDatabasePort;
import com.rviewer.skeletons.infrastructure.database.dto.PostgresCart;
import com.rviewer.skeletons.infrastructure.database.repository.PostgresCartRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CartDatabaseAdapter implements CartDatabasePort {

    private final PostgresCartRepository postgresCartRepository;

    public CartDatabaseAdapter(PostgresCartRepository postgresCartRepository) {
        this.postgresCartRepository = postgresCartRepository;
    }

    @Override
    public Optional<Cart> get(String id) {
        return postgresCartRepository.findById(UUID.fromString(id)).map(PostgresCart::toDomain);
    }

    @Override
    public void save(Cart cart) {
        postgresCartRepository.save(PostgresCart.fromDomain(cart));
    }

    @Override
    public void delete(String id) {
        postgresCartRepository.deleteById(UUID.fromString(id));
    }
}
