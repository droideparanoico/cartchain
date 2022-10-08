package com.rviewer.skeletons.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CartNotFoundException extends RuntimeException {
    private static final String CART_NOT_FOUND = "Cart not found for the given ID";

    public CartNotFoundException() {
        super(CART_NOT_FOUND);
    }
}
