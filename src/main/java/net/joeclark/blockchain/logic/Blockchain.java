package net.joeclark.blockchain.logic;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;

import net.joeclark.blockchain.model.Chain;

public class Blockchain {
    
    public static final int NETWORK_ID = 42;

    private Chain chain;
    private ConcurrentHashMap blockCache;
    private ConcurrentHashMap transactionCache;
    private BigInteger difficulty;

    public Blockchain() {
        this.chain = new Chain(NETWORK_ID);
        this.blockCache = new ConcurrentHashMap<>();
        this.transactionCache = new ConcurrentHashMap<>();
        this.difficulty = new BigInteger("16000");
    }
}
