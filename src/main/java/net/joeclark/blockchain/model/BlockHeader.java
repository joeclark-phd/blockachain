package net.joeclark.blockchain.model;

import java.io.Serializable;
import java.util.Arrays;

import net.joeclark.blockchain.utils.SHA3Helper;

public class BlockHeader implements Serializable {
    
    private int versionNumber;
    private long timestamp;
    private byte[] previousBlockHash;
    private byte[] transactionListHash;
    private int nonce;

    /** Default constructor is needed for serialization/deserialization. */
    public BlockHeader() {}
    
    public BlockHeader(long timestamp, byte[] previousBlockHash) {
        this.versionNumber = 1;
        this.timestamp = timestamp;
        this.previousBlockHash = previousBlockHash;
        this.nonce = 1;
    }

    public BlockHeader(int versionNumber, long timestamp, byte[] previousBlockHash, byte[] transactionListHash, int nonce) {
        this.versionNumber = 1;
        this.timestamp = timestamp;
        this.previousBlockHash = previousBlockHash;
        this.transactionListHash = transactionListHash;
        this.nonce = 0;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getPreviousBlockHash() {
        return previousBlockHash;
    }

    public void setPreviousBlockHash(byte[] previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
    }

    public byte[] getTransactionListHash() {
        return transactionListHash;
    }

    public void setTransactionListHash(byte[] transactionListHash) {
        this.transactionListHash = transactionListHash;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }



	@Override
    public String toString() {
        return "BlockHeader [versionNumber=" + versionNumber + ", timestamp=" + timestamp + ", previousBlockHash="
                + Arrays.toString(previousBlockHash) + ", transactionListHash=" + Arrays.toString(transactionListHash)
                + ", nonce=" + nonce + "]";
    }

    public byte[] asHash( ){
		return SHA3Helper.hash256(this);
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + versionNumber;
        result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
        result = prime * result + Arrays.hashCode(previousBlockHash);
        result = prime * result + Arrays.hashCode(transactionListHash);
        result = prime * result + nonce;
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
        BlockHeader other = (BlockHeader) obj;
        if (versionNumber != other.versionNumber)
            return false;
        if (timestamp != other.timestamp)
            return false;
        if (!Arrays.equals(previousBlockHash, other.previousBlockHash))
            return false;
        if (!Arrays.equals(transactionListHash, other.transactionListHash))
            return false;
        if (nonce != other.nonce)
            return false;
        return true;
    }

}
