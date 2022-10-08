package com.rviewer.skeletons.infrastructure.outbound.database.adapter;

import com.rviewer.skeletons.domain.model.Cart;
import com.rviewer.skeletons.domain.ports.secondary.DatabasePort;
import com.rviewer.skeletons.infrastructure.outbound.database.repository.PostgresCartRepository;
import com.rviewer.skeletons.infrastructure.outbound.database.dto.PostgresCart;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PostgresAdapter implements DatabasePort {

    private final PostgresCartRepository postgresCartRepository;

    public PostgresAdapter(PostgresCartRepository postgresCartRepository) {
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
