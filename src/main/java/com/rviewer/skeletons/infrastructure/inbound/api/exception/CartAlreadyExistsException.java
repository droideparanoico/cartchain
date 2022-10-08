package com.rviewer.skeletons.infrastructure.inbound.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CartAlreadyExistsException extends RuntimeException {
    private static final String CART_ALREADY_EXISTS = "Invalid identifier.";

    public CartAlreadyExistsException() {
        super(CART_ALREADY_EXISTS);
    }
}
