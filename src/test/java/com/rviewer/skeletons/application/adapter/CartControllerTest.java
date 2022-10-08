package com.rviewer.skeletons.application.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rviewer.skeletons.domain.model.Cart;
import com.rviewer.skeletons.domain.model.Item;
import com.rviewer.skeletons.domain.ports.inbound.CartServicePort;
import com.rviewer.skeletons.application.exception.CartAlreadyExistsException;
import com.rviewer.skeletons.application.exception.CartNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.rviewer.skeletons.application.exception.ControllerExceptionHandler.INVALID_BODY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CartServicePort cartServicePort;

    @Test
    void should_return_cart() throws Exception {
        UUID id = UUID.randomUUID();
        Item item = new Item(UUID.randomUUID(), "item", 1, 100F);
        Cart expectedCart = new Cart(id, List.of(item));

        when(cartServicePort.get(id.toString())).thenReturn(Optional.of(expectedCart));

        mockMvc.perform(MockMvcRequestBuilders.get("/carts/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.items[0].id").value(item.getId().toString()))
                .andExpect(jsonPath("$.items[0].name").value(item.getName()))
                .andExpect(jsonPath("$.items[0].quantity").value(item.getQuantity()))
                .andExpect(jsonPath("$.items[0].price").value(item.getPrice()));
    }

    @Test
    void should_not_return_non_existing_cart() throws Exception {
        UUID id = UUID.randomUUID();

        when(cartServicePort.get(id.toString())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/carts/" + id))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CartNotFoundException));
    }

    @Test
    void should_save_new_cart() throws Exception {
        UUID id = UUID.randomUUID();
        Item item = new Item(UUID.randomUUID(), "item", 1, 100F);
        Cart newCart = new Cart(id, List.of(item));

        mockMvc.perform(MockMvcRequestBuilders.post("/carts/" + id)
                .content(asJsonString(newCart))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void should_not_save_old_cart() throws Exception {
        UUID id = UUID.randomUUID();
        Item item = new Item(UUID.randomUUID(), "item", 1, 100F);
        Cart oldCart = new Cart(id, List.of(item));

        when(cartServicePort.get(id.toString())).thenReturn(Optional.of(oldCart));

        mockMvc.perform(MockMvcRequestBuilders.post("/carts/" + id)
                        .content(asJsonString(oldCart))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CartAlreadyExistsException))
        ;
    }

    @Test
    void should_not_save_null_cart() throws Exception {
        UUID id = UUID.randomUUID();
        Cart nullCart = new Cart(id, null);

        mockMvc.perform(MockMvcRequestBuilders.post("/carts/" + id)
                        .content(asJsonString(nullCart))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertEquals(INVALID_BODY, result.getResponse().getContentAsString()));
    }

    @Test
    void should_not_save_bad_cart() throws Exception {
        UUID id = UUID.randomUUID();
        Item item = new Item(UUID.randomUUID(), "item", 1, 100F);
        Cart badCart = new Cart(id, List.of(item));

        mockMvc.perform(MockMvcRequestBuilders.post("/carts/" + id)
                        .content(asJsonString(badCart).replace("100.0", ""))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpMessageNotReadableException))
                .andExpect(result -> assertEquals(INVALID_BODY, result.getResponse().getContentAsString()));
    }

    @Test
    void should_update_old_cart() throws Exception {
        UUID id = UUID.randomUUID();
        Item item = new Item(UUID.randomUUID(), "item", 1, 100F);
        Cart oldCart = new Cart(id, List.of(item));

        when(cartServicePort.get(id.toString())).thenReturn(Optional.of(oldCart));

        mockMvc.perform(MockMvcRequestBuilders.patch("/carts/" + id)
                        .content(asJsonString(oldCart).replace("100.0", "1000.0"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(1000F, cartServicePort.get(id.toString()).orElseThrow().getItems().get(0).getPrice()));
    }

    @Test
    void should_not_update_non_existing_cart() throws Exception {
        UUID id = UUID.randomUUID();
        Item item = new Item(UUID.randomUUID(), "item", 1, 100F);
        Cart nonExistingCart = new Cart(id, List.of(item));

        when(cartServicePort.get(id.toString())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.patch("/carts/" + id)
                .content(asJsonString(nonExistingCart))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CartNotFoundException));
    }

    @Test
    void should_not_update_null_cart() throws Exception {
        UUID id = UUID.randomUUID();
        Cart nullCart = new Cart(id, null);

        mockMvc.perform(MockMvcRequestBuilders.patch("/carts/" + id)
                        .content(asJsonString(nullCart))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertEquals(INVALID_BODY, result.getResponse().getContentAsString()));
    }

    @Test
    void should_not_update_bad_cart() throws Exception {
        UUID id = UUID.randomUUID();
        Item item = new Item(UUID.randomUUID(), "item", 1, 100F);
        Cart badCart = new Cart(id, List.of(item));

        mockMvc.perform(MockMvcRequestBuilders.patch("/carts/" + id)
                        .content(asJsonString(badCart).replace("100.0", ""))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpMessageNotReadableException))
                .andExpect(result -> assertEquals(INVALID_BODY, result.getResponse().getContentAsString()))
        ;
    }

    @Test
    void should_delete_old_cart() throws Exception {
        UUID id = UUID.randomUUID();
        Item item = new Item(UUID.randomUUID(), "item", 1, 100F);
        Cart oldCart = new Cart(id, List.of(item));

        when(cartServicePort.get(id.toString())).thenReturn(Optional.of(oldCart));

        mockMvc.perform(MockMvcRequestBuilders.delete("/carts/" + id)
                        .content(asJsonString(oldCart))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_delete_non_existing_cart() throws Exception {
        UUID id = UUID.randomUUID();
        Item item = new Item(UUID.randomUUID(), "item", 1, 100F);
        Cart nonExistingCart = new Cart(id, List.of(item));

        when(cartServicePort.get(id.toString())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete("/carts/" + id)
                        .content(asJsonString(nonExistingCart))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CartNotFoundException));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
