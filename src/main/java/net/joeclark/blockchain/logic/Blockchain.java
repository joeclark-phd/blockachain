package net.joeclark.blockchain.logic;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;

import net.joeclark.blockchain.model.Block;
import net.joeclark.blockchain.model.Chain;

public class Blockchain {
    
    public static final int NETWORK_ID = 42;
    public static final String HARDCODED_DIFFICULTY = "-57895900000000000000000000000000000000000000000000000000000000000000000000000";

    private Chain chain;
    private ConcurrentHashMap blockCache;
    private ConcurrentHashMap transactionCache;
    private BigInteger difficulty;

    public Blockchain() {
        this.chain = new Chain(NETWORK_ID);
        this.blockCache = new ConcurrentHashMap<>();
        this.transactionCache = new ConcurrentHashMap<>();
        this.difficulty = new BigInteger(HARDCODED_DIFFICULTY);
    }

    public byte[] getLastHash() {
        return getLastBlock().getBlockHash();
    }

    private Block getLastBlock() {
        return this.chain.getBlocks().getLast();
    }

    public boolean fulfillsDifficulty(byte[] digest) {
		BigInteger temp = new BigInteger( digest );
		return temp.compareTo( difficulty ) <= 0;
	}

    public void addBlock(Block block) {
        this.chain.addBlock(block);
    }
}
