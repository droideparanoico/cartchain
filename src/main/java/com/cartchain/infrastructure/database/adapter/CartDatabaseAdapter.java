package com.cartchain.infrastructure.database.adapter;

import com.cartchain.domain.model.Cart;
import com.cartchain.domain.ports.outbound.CartDatabasePort;
import com.cartchain.infrastructure.database.dto.PostgresCart;
import com.cartchain.infrastructure.database.repository.PostgresCartRepository;
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
