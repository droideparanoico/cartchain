package com.rviewer.skeletons.application.adapter;

import com.rviewer.skeletons.application.exception.CartAlreadyExistsException;
import com.rviewer.skeletons.application.exception.CartNotFoundException;
import com.rviewer.skeletons.domain.model.Cart;
import com.rviewer.skeletons.domain.model.Item;
import com.rviewer.skeletons.domain.ports.inbound.CartServicePort;
import com.rviewer.skeletons.application.request.CreateCartReq;
import com.rviewer.skeletons.application.request.UpdateCartReq;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartServicePort cartServicePort;

    public CartController(CartServicePort cartServicePort) {
        this.cartServicePort = cartServicePort;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCart(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(cartServicePort.get(id).orElseThrow(CartNotFoundException::new));
    }

    @PostMapping("/{id}")
    public ResponseEntity<HttpStatus> saveCart(@PathVariable String id, @Valid @RequestBody CreateCartReq createCartReq) {
        if (cartServicePort.get(id).isPresent()) {
            throw new CartAlreadyExistsException();
        } else {
            cartServicePort.save(new Cart(UUID.fromString(id), createCartReq.getItems()
                    .stream()
                    .map(item -> new Item(item.getId(), item.getName(), item.getQuantity(), item.getPrice()))
                    .toList()));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateCart(@PathVariable String id, @Valid @RequestBody UpdateCartReq updateCartReq) {
        Cart cart = cartServicePort.get(id).orElseThrow(CartNotFoundException::new);
        cart.setItems(updateCartReq.getItems());
        cartServicePort.save(cart);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCart(@PathVariable String id) {
        if (cartServicePort.get(id).isEmpty()) {
            throw new CartNotFoundException();
        } else {
            cartServicePort.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

}
