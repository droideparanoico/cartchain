package com.cartchain.domain.model;

import com.cartchain.domain.exception.HashGenerationException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class Block {
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private String previousHash;
    private String data;
    @Builder.Default
    private Integer nonce = RandomNonce.getRandomNonce();
    private String hash;

    public static Block getGenesisBlock() {
        Block genesisBlock = Block.builder().previousHash("").build();
        genesisBlock.setHash(generateHashFromBlock(genesisBlock));
        return genesisBlock;
    }

    public static String generateHashFromBlock(final Block block) throws HashGenerationException {
        try {
            final var digest = MessageDigest.getInstance("SHA-256");
            final byte[] hashData = digest.digest(block.getHashData().getBytes(StandardCharsets.UTF_8));
            final var hexString = new StringBuilder();
            for (final byte elem : hashData) {
                final var hex = Integer.toHexString(0xff & elem);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (final Exception e) {
            throw new HashGenerationException(e);
        }
    }

    public void generateHash() {
        hash = generateHashFromBlock(this);
    }

    private String getHashData() {
        return timestamp + previousHash + data + nonce;
    }
}
