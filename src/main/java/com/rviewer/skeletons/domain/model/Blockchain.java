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
        if (blockchainList.isEmpty()) {
            return false;
        }
        if (blockchainList.size() < this.blockchainList.size()) {
            return false;
        }
        List<Block> newBlockchainList = blockchainList.subList(0, this.blockchainList.size());
        if (!newBlockchainList.equals(this.blockchainList)) {
            return false;
        }
        return isValid(blockchainList);
    }

    public Boolean isValid(List<Block> blockchainList) {
        boolean valid = true;
        // Validate genesis block
        if (!blockchainList.get(0).getPreviousHash().equals("")) {
            return false;
        }
        // Validate every element's last hash value matches previous block's hash
        for (int i = 0; i < blockchainList.size() - 1; i++) {
            valid = blockchainList.get(i).getHash().equals(blockchainList.get(i + 1).getPreviousHash());
            if (!valid) return false;
        }
        // Validate data hasn't been tampered
        for (Block block : blockchainList) {
            valid = block.getHash().equals(Block.generateHashFromBlock(block));
            if (!valid) break;
        }
        return valid;
    }
}
