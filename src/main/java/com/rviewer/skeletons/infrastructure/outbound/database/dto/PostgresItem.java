package com.rviewer.skeletons.infrastructure.outbound.database.dto;

import com.rviewer.skeletons.domain.model.Item;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@With
public class PostgresItem {
    @Id
    private UUID id;
    private String name;
    private Integer quantity;
    private Float price;

    public static PostgresItem fromDomain(Item item) {
        return new PostgresItem()
                .withId(item.getId())
                .withName(item.getName())
                .withQuantity(item.getQuantity())
                .withPrice(item.getPrice());
    }

    public Item toDomain() {
        return new Item()
                .withId(id)
                .withName(name)
                .withQuantity(quantity)
                .withPrice(price);
    }
}
