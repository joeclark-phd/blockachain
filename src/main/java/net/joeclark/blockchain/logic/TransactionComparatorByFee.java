package net.joeclark.blockchain.logic;

import java.util.Comparator;

import net.joeclark.blockchain.model.Transaction;

public class TransactionComparatorByFee implements Comparator<Transaction> {

    @Override
    public int compare(Transaction o1, Transaction o2) {
        if(o2.getTransactionFeeBasePrice() < o1.getTransactionFeeBasePrice()) {
            return -1;
        } else if(o2.getTransactionFeeBasePrice() > o1.getTransactionFeeBasePrice()) {
            return 1;
        } else {
            return 0;
        }
    }
    
}
