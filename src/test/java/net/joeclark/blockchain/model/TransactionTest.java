package net.joeclark.blockchain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import net.joeclark.blockchain.utils.SHA3Helper;

public class TransactionTest {

    @Test
    public void testHashing() {
        Transaction t = new Transaction("sender","reciever", 99.99, 12345, 1.00, 10.0);
        // hash should be 32 bytes in length
        assertEquals( 32, SHA3Helper.hash256(t).length );
        // we can re-hash the same transaction and get the same output
        assertEquals( SHA3Helper.digestToHex(t.getTxId()), SHA3Helper.digestToHex(SHA3Helper.hash256(t)));
    }

}
