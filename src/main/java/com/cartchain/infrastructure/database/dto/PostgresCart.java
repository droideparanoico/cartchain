package com.cartchain.infrastructure.database.dto;

import com.cartchain.domain.model.Cart;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@With
public class PostgresCart {
    @Id
    private UUID id;
    @OneToMany(cascade= CascadeType.ALL)
    private List<PostgresItem> postgresItems;

    public static PostgresCart fromDomain(Cart cart) {
        return new PostgresCart()
                .withId(cart.getId())
                .withPostgresItems(cart.getItems()
                        .stream()
                        .map(PostgresItem::fromDomain)
                        .toList());
    }

    public Cart toDomain() {
        return new Cart()
                .withId(id)
                .withItems(postgresItems
                        .stream()
                        .map(PostgresItem::toDomain)
                        .toList());
    }
}
