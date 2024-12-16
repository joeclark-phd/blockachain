package net.joeclark.blockchain.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import net.joeclark.blockchain.model.Block;
import net.joeclark.blockchain.model.Transaction;

public class PendingTransactions {
    
    private PriorityQueue<Transaction> pendingTransactions;

    public PendingTransactions() {
        pendingTransactions = new PriorityQueue<>(new TransactionComparatorByFee());
    }

    public void clearPendingTransactions(Block block) {
        for(Transaction transaction: block.getTransactions()) {
            pendingTransactions.remove(transaction);
        }
    }

    public List<Transaction> getTransactionsForNextBlock() {
        List<Transaction> nextTransactions = new ArrayList<>();
        int transactionCapacity = 10; // how many transactions can we fit in a block?
        PriorityQueue<Transaction> tmp = new PriorityQueue<>(pendingTransactions); // copy pendingTransactions into a new queue we can remove things from
        while(transactionCapacity > 0 && !tmp.isEmpty()) {
            nextTransactions.add(tmp.poll()); // take the best transactions (best transaction fee) until the block is full
            transactionCapacity--;
        }
        return nextTransactions;
    }

    public void addPendingTransaction(Transaction transaction) {
        pendingTransactions.add(transaction);
    }

    public boolean pendingTransactionsAvailable() {
        return (pendingTransactions != null && !pendingTransactions.isEmpty());
    }

}
