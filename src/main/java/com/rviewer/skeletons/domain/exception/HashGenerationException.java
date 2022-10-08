package com.rviewer.skeletons.domain.exception;

public class HashGenerationException extends RuntimeException {
    public HashGenerationException(Throwable cause) {
        super("Unable generate hash: " + cause);
    }
}
