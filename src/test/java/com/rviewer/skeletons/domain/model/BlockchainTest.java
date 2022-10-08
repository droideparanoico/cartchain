package com.rviewer.skeletons.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockchainTest {
    private Blockchain blockchain;

    @BeforeEach
    void init() {
        blockchain = new Blockchain();
    }

    @Test
    void should_add_block() {
        Block genesisBlock = Block.getGenesisBlock();
        blockchain.addBlock(genesisBlock);
        assertEquals(blockchain.getBlockchainList().get(0), genesisBlock);
    }

    @Test
    void should_replace_blockchainList() {
        List<Block> newBlockchainList = new ArrayList<>();

        Block genesisBlock = Block.getGenesisBlock();
        blockchain.addBlock(genesisBlock);
        newBlockchainList.add(genesisBlock);

        Block secondBlock = Block.builder().previousHash(genesisBlock.getHash()).data(null).build();
        secondBlock.generateHash();
        blockchain.addBlock(secondBlock);
        newBlockchainList.add(secondBlock);

        Block thirdBlock = Block.builder().previousHash(secondBlock.getHash()).data(null).build();
        thirdBlock.generateHash();
        newBlockchainList.add(thirdBlock);

        assertTrue(blockchain.replace(newBlockchainList));
    }

    @Test
    void should_not_replace_blockchainList_because_is_empty() {
        List<Block> newBlockchainList = new ArrayList<>();

        Block genesisBlock = Block.getGenesisBlock();
        blockchain.addBlock(genesisBlock);

        assertFalse(blockchain.replace(newBlockchainList));
    }

    @Test
    void should_not_replace_blockchainList_because_smaller() {
        List<Block> newBlockchainList = new ArrayList<>();

        Block genesisBlock = Block.getGenesisBlock();
        blockchain.addBlock(genesisBlock);
        newBlockchainList.add(genesisBlock);

        Block secondBlock = Block.builder().previousHash(genesisBlock.getHash()).data(null).build();
        secondBlock.generateHash();
        blockchain.addBlock(secondBlock);

        assertFalse(blockchain.replace(newBlockchainList));
    }

    @Test
    void should_not_replace_blockchainList_because_not_equals() {
        List<Block> newBlockchainList = new ArrayList<>();

        Block genesisBlock = Block.getGenesisBlock();
        blockchain.addBlock(genesisBlock);
        newBlockchainList.add(genesisBlock);

        Block secondBlock = Block.builder().previousHash(genesisBlock.getHash()).data(null).build();
        secondBlock.generateHash();
        blockchain.addBlock(secondBlock);
        newBlockchainList.add(Block.builder().previousHash(genesisBlock.getHash()).data("data").build());

        assertFalse(blockchain.replace(newBlockchainList));
    }

    @Test
    void should_not_replace_blockchainList_because_invalid_genesis() {
        List<Block> newBlockchainList = new ArrayList<>();

        Block genesisBlock = Block.builder().previousHash("non empty").data(null).build();
        genesisBlock.generateHash();
        blockchain.addBlock(genesisBlock);
        newBlockchainList.add(genesisBlock);

        Block secondBlock = Block.builder().previousHash(genesisBlock.getHash()).data(null).build();
        secondBlock.generateHash();
        blockchain.addBlock(secondBlock);
        newBlockchainList.add(secondBlock);

        assertFalse(blockchain.replace(newBlockchainList));
    }

    @Test
    void should_not_replace_blockchainList_because_hash_mismatch() {
        List<Block> newBlockchainList = new ArrayList<>();

        Block genesisBlock = Block.getGenesisBlock();
        blockchain.addBlock(genesisBlock);
        newBlockchainList.add(genesisBlock);

        Block secondBlock = Block.builder().previousHash("wrong hash").data(null).build();
        secondBlock.generateHash();
        blockchain.addBlock(secondBlock);
        newBlockchainList.add(secondBlock);

        assertFalse(blockchain.replace(newBlockchainList));
    }

    @Test
    void should_not_replace_blockchainList_because_tampered_data() {
        List<Block> newBlockchainList = new ArrayList<>();

        Block genesisBlock = Block.getGenesisBlock();
        blockchain.addBlock(genesisBlock);
        newBlockchainList.add(genesisBlock);

        Block secondBlock = Block.builder().previousHash(genesisBlock.getHash()).data(null).build();
        secondBlock.generateHash();
        blockchain.addBlock(secondBlock);
        secondBlock.setData("tampered data");
        newBlockchainList.add(secondBlock);

        assertFalse(blockchain.replace(newBlockchainList));
    }
}
