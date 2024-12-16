package net.joeclark.blockchain.threads;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import net.joeclark.blockchain.logic.DependencyInjector;
import net.joeclark.blockchain.logic.PendingTransactions;
import net.joeclark.blockchain.model.Block;
import net.joeclark.blockchain.model.Transaction;

public class MinerTest implements MinerListener {

    @BeforeAll
    public static void setUp() {
        PendingTransactions pendingTransactions = DependencyInjector.getPendingTransactions();
        for(int i=0; i< 100; i++) {
            // create some transactions
            String sender = "testSender"+i;
            String recipient = "testRecipient"+i;
            double amount = i*1.1;
            double transactionFee = 0.0000001 * i;
            pendingTransactions.addPendingTransaction(
                new Transaction(sender.getBytes(), recipient.getBytes(), amount, 1, transactionFee, 10.0)
            );
        }
    }

    @Test
    public void testMiner() throws InterruptedException {
        Miner miner = new Miner();
        miner.registerListener(this);
        Thread thread = new Thread(miner);
        thread.start();
        while(DependencyInjector.getPendingTransactions().pendingTransactionsAvailable()) {
            Thread.sleep(1000);
        }
        miner.stopMining();
    }

    @Override
    public void notifyNewBlock(Block block) {
        System.out.println(">>> new block mined with nonce: "+block.getBlockHeader().getNonce());
    }


}
