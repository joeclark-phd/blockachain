package net.joeclark.blockchain.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.owlike.genson.Genson;

import net.joeclark.blockchain.model.Block;
import net.joeclark.blockchain.model.Chain;
import net.joeclark.blockchain.model.GenesisBlock;
import net.joeclark.blockchain.utils.SHA3Helper;

/** Class to manage writing the blockchain to (and reading back from) disk storage. */
public class Persistence {

    private Charset encoding = StandardCharsets.UTF_8;
    private String path = "e:/chains/";

    public void writeChain(Chain chain) throws IOException {
        // empty the directory, for testing
        deleteChain(chain.getNetworkId());
        // write all the blocks to individual files
        for(Block block: chain.getBlocks()) {
            String id = SHA3Helper.digestToHex(block.getBlockHash());
            writeBlock(block, chain.getNetworkId(), id);
        }
    }

    private void deleteChain(int networkid) {
        File folder = new File(getPathToChain(networkid));
        for(File file: folder.listFiles()) {
            file.delete();
        }
    }

    private void writeBlock(Block block, int networkId, String id) throws FileNotFoundException {
        File file = new File(getPathToBlock(networkId, id));
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), encoding);
        Genson genson = new Genson();
        genson.serialize(block, outputStreamWriter);
    }


	private String getPathToChain(int networkId) {
		return path + networkId;
	}

	private String getPathToBlock(int networkId, String blockId) {
		return path + networkId + "/" + blockId + ".json";
	}

    public Chain readChain(int networkId) throws Exception {
        File folder = new File(getPathToChain(networkId));
        File[] files = folder.listFiles();
        // read all files from directory as Blocks
        List<Block> blocks = new ArrayList<>();
        for(File file: files) {
            blocks.add(readBlock(file));
        }
        // Sort the blocks and build the Chain object
        return new Chain(networkId, sortedBlocks(blocks));
    }

    private Block readBlock(File file) throws FileNotFoundException {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), encoding);
        Genson genson = new Genson();
        return genson.deserialize(inputStreamReader, Block.class);
    }

    /** 
     * Each block holds the hash of the previous block.  We can use this principle to put them in order
     * and re-assemble the chain.
     * This is probably  not the most efficient way to do this, since it goes through the whole chain as many times as there are blocks.
     */
        private List<Block> sortedBlocks(List<Block> unsortedBlocks) throws Exception {
        List<Block> workingList = new ArrayList<>();
        // first find the genesis block
        for(Block b: unsortedBlocks) {
            if(Arrays.equals(b.getBlockHeader().getPreviousBlockHash(), GenesisBlock.ZERO_HASH)) {
                workingList.add(b);
                break;
            }
        }
        if(workingList.size()==0) {
            throw new Exception("No genesis block found");
        }
        // now search for the block following the last one, until we've gone through this process unsortedBlocks.size()-1 times
        for(int i=1; i<unsortedBlocks.size(); i++) {
            byte[] hashToMatch = workingList.get(workingList.size()-1).getBlockHash();
            for(Block b: unsortedBlocks) {
                if(Arrays.equals(b.getBlockHeader().getPreviousBlockHash(),hashToMatch)) {
                    workingList.add(b);
                    break;
                }
            }
        }
        // check that the resulting list is the same length (nothing went wrong)
        if(workingList.size() == unsortedBlocks.size()) {
            return workingList;
        } else {
            throw new Exception("Something went wrong in sorting the chain.");
        }
    }

}
