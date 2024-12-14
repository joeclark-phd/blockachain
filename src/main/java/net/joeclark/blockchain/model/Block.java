package net.joeclark.blockchain.model;

import java.io.Serializable;
import java.util.ArrayList;
import org.bouncycastle.util.Arrays;
import java.util.List;

import net.joeclark.blockchain.utils.SHA3Helper;

public class Block implements Serializable {

    private int magicNumber = 0x7B908F61; // arbitrary number to identify my unique protocol
    private int blockSize;
    private int transactionCount;
    private List<Transaction> transactions;
    private BlockHeader blockHeader; 

    /** Default constructor is needed for serialization/deserialization. */
    public Block() {
    }

    public Block(byte[] previousBlockHash) {
        this.blockSize = 92; // 80 byte BlockHeader + 3 x 4-byte integers
        this.transactions = new ArrayList<>();
        this.transactionCount = 0;
        this.blockHeader = new BlockHeader(System.currentTimeMillis(), previousBlockHash);
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        this.transactionCount++;
        this.blockHeader.setTransactionListHash(getTransactionHash());
        this.blockSize += 128; // TODO: confirm that our "Transaction" is 128 bytes
    }

    /** Concatenates all of the individual transaction hashes, and hashes the result. */
    private byte[] getTransactionHash( ) {
        byte[] transactionsInBytes = new byte[0];
        for (Transaction transaction : transactions){
            transactionsInBytes = Arrays.concatenate( transactionsInBytes, transaction.getTxId() );
        }
        return SHA3Helper.hash256(transactionsInBytes);
    }

    /** 
     * The block's hash is really the block header's hash.  Since it contains a hash of the 
     * transaction array, it prevents later tampering with the transactions. 
     */
    public byte[] getBlockHash( ) {
		return blockHeader.asHash( );
	}

    public int getMagicNumber() {
        return magicNumber;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public BlockHeader getBlockHeader() {
        return blockHeader;
    }

    public void setBlockHeader(BlockHeader blockHeader) {
        this.blockHeader = blockHeader;
    }

    public void setMagicNumber(int magicNumber) {
        this.magicNumber = magicNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + magicNumber;
        result = prime * result + blockSize;
        result = prime * result + transactionCount;
        result = prime * result + ((transactions == null) ? 0 : transactions.hashCode());
        result = prime * result + ((blockHeader == null) ? 0 : blockHeader.hashCode());
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
        Block other = (Block) obj;
        if (magicNumber != other.magicNumber)
            return false;
        if (blockSize != other.blockSize)
            return false;
        if (transactionCount != other.transactionCount)
            return false;
        if (transactions == null) {
            if (other.transactions != null)
                return false;
        } else if (!transactions.equals(other.transactions))
            return false;
        if (blockHeader == null) {
            if (other.blockHeader != null)
                return false;
        } else if (!blockHeader.equals(other.blockHeader))
            return false;
        return true;
    }

    



}
