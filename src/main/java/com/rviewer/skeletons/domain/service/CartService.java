package com.rviewer.skeletons.domain.service;

import com.rviewer.skeletons.domain.model.Block;
import com.rviewer.skeletons.domain.model.Blockchain;
import com.rviewer.skeletons.domain.model.Cart;
import com.rviewer.skeletons.domain.ports.inbound.CartServicePort;
import com.rviewer.skeletons.domain.ports.outbound.CartDatabasePort;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@Transactional
public class CartService implements CartServicePort {
    private final CartDatabasePort cartDatabasePort;
    private final Blockchain blockchain;

    public CartService(CartDatabasePort cartDatabasePort, Blockchain blockchain) {
        this.cartDatabasePort = cartDatabasePort;
        this.blockchain = blockchain;
    }

    @Override
    public Optional<Cart> get(String id) {
        return cartDatabasePort.get(id);
    }

    @Override
    public void save(Cart cart) {
        cartDatabasePort.save(cart);
        if (blockchain.getBlockchainList().isEmpty()) {
            blockchain.addBlock(Block.getGenesisBlock().withData(cart.toString()));
        } else {
            blockchain.addBlock(new Block().withData(cart.toString())
                    .withLasthash(blockchain.getBlockchainList().getLast().getHash()));
        }
    }

    @Override
    public void delete(String id) {
        cartDatabasePort.delete(id);
        blockchain.addBlock(new Block().withData("deleted cart with id:" + id)
                .withLasthash(blockchain.getBlockchainList().getLast().getHash()));
    }
}
