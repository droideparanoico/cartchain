package com.cartchain.domain.model;

import java.util.Random;

public class RandomNonce {
    private static final Random RANDOM = new Random();

    private RandomNonce() {}

    public static Integer getRandomNonce() {
        return RANDOM.nextInt();
    }
}
