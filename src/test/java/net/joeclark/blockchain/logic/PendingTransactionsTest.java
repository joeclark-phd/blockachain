package net.joeclark.blockchain.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.joeclark.blockchain.model.Transaction;

public class PendingTransactionsTest {

    @Test
    public void testHighestFeeTransactionsAreChosenFirst() {
        Transaction nerfTransaction = new Transaction("joe","clark",100.0,1,0.99,5.0);
        Transaction buffTransaction = new Transaction("clark","joe",100.0,1,2.0,5.0);
        PendingTransactions pendingTransactions = new PendingTransactions();
        pendingTransactions.addPendingTransaction(buffTransaction);
        pendingTransactions.addPendingTransaction(nerfTransaction);

        List<Transaction> sortedTransactions = pendingTransactions.getTransactionsForNextBlock();
        assertEquals(sortedTransactions.get(0), buffTransaction);
    }

}
