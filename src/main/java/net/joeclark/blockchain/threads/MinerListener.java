package net.joeclark.blockchain.threads;

import net.joeclark.blockchain.model.Block;

/** Interface for classes that want to subscribe to the Miner and know when new blocks are added. */
public interface MinerListener {

    void notifyNewBlock(Block block);

}
