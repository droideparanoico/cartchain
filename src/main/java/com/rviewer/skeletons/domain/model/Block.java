package com.rviewer.skeletons.domain.model;

import com.rviewer.skeletons.domain.exception.HashGenerationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Random;

@Data
@AllArgsConstructor
@With
public class Block {
    private LocalDateTime timestamp;
    private String lasthash;
    private String data;
    private Integer nonce;
    private String hash;

    public Block() {
        this.timestamp = LocalDateTime.now();
        this.nonce = new Random().nextInt();
        this.hash = generateHashFromBlock(this);
    }

    public static Block getGenesisBlock() {
        return new Block().withLasthash("");
    }

    static String generateHashFromBlock(final Block block) throws HashGenerationException {
        try {
            final var digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(block.getHashData().getBytes(StandardCharsets.UTF_8));
            final var hexString = new StringBuilder();
            for (final byte elem : hash) {
                final var hex = Integer.toHexString(0xff & elem);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (final Exception e) {
            throw new HashGenerationException(e);
        }
    }

    private String getHashData() {
        return timestamp + lasthash + data + nonce;
    }
}
