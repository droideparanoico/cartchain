package com.rviewer.skeletons.infrastructure.outbound.adapter;

import com.rviewer.skeletons.domain.model.Cart;
import com.rviewer.skeletons.domain.model.Item;
import com.rviewer.skeletons.infrastructure.outbound.database.adapter.PostgresAdapter;
import com.rviewer.skeletons.infrastructure.outbound.database.dto.PostgresCart;
import com.rviewer.skeletons.infrastructure.outbound.database.repository.PostgresCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostgresAdapterTest {

    @Autowired
    private PostgresAdapter postgresAdapter;
    @Mock
    private PostgresCartRepository postgresCartRepository;

    @BeforeEach
    void init() {
        postgresAdapter = new PostgresAdapter(postgresCartRepository);
    }

    @Test
    void should_get_cart() {
        UUID id = UUID.randomUUID();
        Item item = new Item(UUID.randomUUID(), "item", 1, 100F);
        Cart expectedCart = new Cart(id, List.of(item));

        when(postgresCartRepository.findById(id)).thenReturn(Optional.ofNullable(PostgresCart.fromDomain(expectedCart)));

        Optional<Cart> result = postgresAdapter.get(id.toString());
        assertThat(Optional.of(result)).hasValue(Optional.of(expectedCart));
    }

    @Test
    void should_save_cart() {
        UUID id = UUID.randomUUID();
        Item item = new Item(UUID.randomUUID(), "item", 1, 100F);
        Cart newCart = new Cart(id, List.of(item));

        postgresAdapter.save(newCart);

        ArgumentCaptor<PostgresCart> captor = ArgumentCaptor.forClass(PostgresCart.class);
        verify(postgresCartRepository).save(captor.capture());
        assertEquals(captor.getValue().toDomain(), newCart);
    }

    @Test
    void should_delete_cart() {
        UUID id = UUID.randomUUID();
        Item item = new Item(UUID.randomUUID(), "item", 1, 100F);
        Cart cartToDelete = new Cart(id, List.of(item));

        when(postgresCartRepository.findById(id)).thenReturn(Optional.ofNullable(PostgresCart.fromDomain(cartToDelete)));

        postgresAdapter.delete(cartToDelete.getId().toString());

        verify(postgresCartRepository, times(1)).deleteById(cartToDelete.getId());
    }
}
