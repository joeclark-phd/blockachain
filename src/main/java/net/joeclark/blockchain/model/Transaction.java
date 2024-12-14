package net.joeclark.blockchain.model;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Arrays;

import net.joeclark.blockchain.utils.SHA3Helper;

/** Our blockchain's blocks will hold a set of simple transactions, moving "amounts" from senders to recipients. */
public class Transaction implements Serializable {

    private byte[] sender;
    private byte[] recipient;
    private double amount;
    private int nonce;
    private double transactionFeeBasePrice;
    private double transactionFeeLimit;
    private transient byte[] txId;

    /** Default constructor is needed for serialization/deserialization. */
    public Transaction() {}

    public Transaction(byte[] sender, byte[] recipient, double amount, int nonce, double transactionFeeBasePrice, double transactionFeeLimit) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.nonce = nonce;
        this.transactionFeeBasePrice = transactionFeeBasePrice;
        this.transactionFeeLimit = transactionFeeLimit;
        this.txId = SHA3Helper.hash256(this); // hash of the transaction so it can't be tampered with
    }

    /** A constructor that accepts Strings for sender and recipient, converts them to bytes and calls the main constructor. */
    public Transaction(String sender, String recipient, double amount, int nonce, double transactionFeeBasePrice, double transactionFeeLimit) {
        this( 
            sender.getBytes( Charset.forName( "utf8" ) ), 
            recipient.getBytes( Charset.forName( "utf8" )), 
            amount, nonce, transactionFeeBasePrice, transactionFeeLimit
        );
    }

    public byte[] getSender() {
        return sender;
    }

    public void setSender(byte[] sender) {
        this.sender = sender;
    }

    public byte[] getRecipient() {
        return recipient;
    }

    public void setRecipient(byte[] recipient) {
        this.recipient = recipient;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public double getTransactionFeeBasePrice() {
        return transactionFeeBasePrice;
    }

    public void setTransactionFeeBasePrice(double transactionFeeBasePrice) {
        this.transactionFeeBasePrice = transactionFeeBasePrice;
    }

    public double getTransactionFeeLimit() {
        return transactionFeeLimit;
    }

    public void setTransactionFeeLimit(double transactionFeeLimit) {
        this.transactionFeeLimit = transactionFeeLimit;
    }

    public byte[] getTxId() {
        return txId;
    }

    public void setTxId(byte[] txId) {
        this.txId = txId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(sender);
        result = prime * result + Arrays.hashCode(recipient);
        long temp;
        temp = Double.doubleToLongBits(amount);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + nonce;
        temp = Double.doubleToLongBits(transactionFeeBasePrice);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(transactionFeeLimit);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + Arrays.hashCode(txId);
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
        Transaction other = (Transaction) obj;
        if (!Arrays.equals(sender, other.sender))
            return false;
        if (!Arrays.equals(recipient, other.recipient))
            return false;
        if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
            return false;
        if (nonce != other.nonce)
            return false;
        if (Double.doubleToLongBits(transactionFeeBasePrice) != Double.doubleToLongBits(other.transactionFeeBasePrice))
            return false;
        if (Double.doubleToLongBits(transactionFeeLimit) != Double.doubleToLongBits(other.transactionFeeLimit))
            return false;
        if (!Arrays.equals(txId, other.txId))
            return false;
        return true;
    }

    
    
}
