package net.joeclark.blockchain.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.joeclark.blockchain.model.Block;
import net.joeclark.blockchain.model.Chain;
import net.joeclark.blockchain.model.GenesisBlock;

public class PersistenceTest {

    @Test
    public void testWrite() throws Exception {

        // create a blockchain
        GenesisBlock firstBlock = new GenesisBlock();
        Block secondBlock = new Block(firstBlock.getBlockHash());
        Chain myChain = new Chain(42, List.of(firstBlock, secondBlock));

        // write it
        Persistence persistence = new Persistence();
        persistence.writeChain(myChain);

        // read it
        Chain chainFromDisk = persistence.readChain(42);

        assertEquals(myChain.getBlocks().size(), chainFromDisk.getBlocks().size());
        assertTrue(myChain.equals(chainFromDisk));


    }
}
