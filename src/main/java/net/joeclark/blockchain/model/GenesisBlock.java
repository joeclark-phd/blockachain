package net.joeclark.blockchain.model;

public class GenesisBlock extends Block {

    public static byte[] ZERO_HASH = new byte[32];

    public GenesisBlock() {
        super(ZERO_HASH);
    }
    
}
