package com.cartchain.infrastructure.database.repository;

import com.cartchain.infrastructure.database.dto.PostgresCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostgresCartRepository extends CrudRepository<PostgresCart, Long> {
    Optional<PostgresCart> findById(UUID id);
    void deleteById(UUID id);
}
