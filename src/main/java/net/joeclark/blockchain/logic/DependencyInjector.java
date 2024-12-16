package net.joeclark.blockchain.logic;

/**
 * A dependency manager that provides singleton implementations of PendingTransactions and Blockchain.
 */
public class DependencyInjector {
    
    private static PendingTransactions pendingTransactions;
    public static PendingTransactions getPendingTransactions() {
        if(pendingTransactions==null) {
            pendingTransactions = new PendingTransactions();
        }
        return pendingTransactions;
    }
    public static void setPendingTransactions(PendingTransactions pendingTransactions) {
        DependencyInjector.pendingTransactions = pendingTransactions;
    }

    private static Blockchain blockchain;
    public static Blockchain getBlockchain() {
        if(blockchain==null) {
            blockchain = new Blockchain();
        }
        return blockchain;
    }
    public static void setBlockchain(Blockchain blockchain) {
        DependencyInjector.blockchain = blockchain;
    }

}
