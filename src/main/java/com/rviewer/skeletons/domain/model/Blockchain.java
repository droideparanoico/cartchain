package com.rviewer.skeletons.domain.model;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class Blockchain {
    private final LinkedList<Block> blockchainList = new LinkedList<>();

    public LinkedList<Block> getBlockchainList() {
        return blockchainList;
    }

    public Block addBlock(Block block) {
        blockchainList.add(block);
        return blockchainList.getLast();
    }

    public Boolean replace(List<Block> blockchainList) {
        return true;
    }

    public Boolean isValid(List<Block> blockchainList) {
        return true;
    }
}
