package net.joeclark.blockchain.threads;

import java.util.ArrayList;
import java.util.List;

import net.joeclark.blockchain.logic.Blockchain;
import net.joeclark.blockchain.logic.DependencyInjector;
import net.joeclark.blockchain.logic.PendingTransactions;
import net.joeclark.blockchain.model.Block;
import net.joeclark.blockchain.model.Transaction;

/** Thread that continuously mines and adds new blocks to the blockchain. */
public class Miner implements Runnable {

    private Block workingBlock;
    private List<MinerListener> listeners = new ArrayList<>();
    private boolean miningStopped = false;

    @Override
    public void run() {
        while(!miningStopped) {

            // create a candidate block (with transactions, if any are pending)
            workingBlock = getNewBlockForMining();
            // increment the nonce until it meets the difficulty
            while(!fulfillsDifficultyRequirement(workingBlock.getBlockHash())) {
                workingBlock.incrementNonce();
            }
            // add the block and alert listeners about the successful mining
            mineBlock(workingBlock);

        }
    }

    private Block getNewBlockForMining() {
        Blockchain blockchain = DependencyInjector.getBlockchain();
        PendingTransactions pendingTransactions = DependencyInjector.getPendingTransactions();
        return new Block(blockchain.getLastHash(), pendingTransactions.getTransactionsForNextBlock());
    }

    private boolean fulfillsDifficultyRequirement(byte[] blockHash) {
        Blockchain blockchain = DependencyInjector.getBlockchain();
        return blockchain.fulfillsDifficulty(blockHash);
    }

    private void mineBlock(Block newBlock) {
        // if there are transactions, set each one to have the block id
        if(newBlock.getTransactionCount()>0) {
            for(Transaction transaction: newBlock.getTransactions()) {
                transaction.setBlockId(newBlock.getBlockHash());
            }
        }
        // add the new block to the chain
        Blockchain blockchain = DependencyInjector.getBlockchain();
        blockchain.addBlock(newBlock);
        // remove transactions from pendingTransactions if they're in the mined block
        PendingTransactions pendingTransactions = DependencyInjector.getPendingTransactions();
        pendingTransactions.clearPendingTransactions(newBlock);
        // notify listeners
        for(MinerListener listener: listeners) {
            listener.notifyNewBlock(newBlock);
        }
    }

    public void registerListener(MinerListener minerListener) {
        listeners.add(minerListener);
    }

    public void stopMining() {

    }

    
}
