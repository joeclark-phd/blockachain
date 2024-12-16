package net.joeclark.blockchain.model;

import java.util.ArrayList;
import java.util.List;

public class Chain {
    
    private int networkId; // Identifies the blockchain. We could use a different networkId for a test chain, for example.
    private List<Block> blocks;
    
    /** Default constructor is needed for serialization/deserialization. */
    public Chain() {}

    public Chain(int networkId) {
        this.networkId = networkId;
        this.blocks = new ArrayList<>();
        blocks.add(new GenesisBlock());
    }

    public Chain(int networkId, List<Block> blocks) {
        this.networkId = networkId;
        this.blocks = blocks;
    }

    public void addBlock(Block block) {
        // could do some error handling here to make sure hash matches last block
        this.blocks.add(block);
    }

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + networkId;
        result = prime * result + ((blocks == null) ? 0 : blocks.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Chain other = (Chain) obj;
        if (networkId != other.networkId)
            return false;
        if (blocks == null) {
            if (other.blocks != null)
                return false;
        } else if (!blocks.equals(other.blocks))
            return false;
        return true;
    }

    

}
