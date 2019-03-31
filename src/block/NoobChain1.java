package block;


import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.gson.GsonBuilder;

public class NoobChain1 {
	
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;

	static Logger logger = Logger.getLogger(NoobChain1.class.getName());
	public static void main(String[] args) {	
		//add our blocks to the blockchain ArrayList:
		
		
		Block genesisBlock =new Block("Hi im the first block", "0"); 
		blockchain.add(genesisBlock);
		logger.info("Trying to Mine block 1... ");
		blockchain.get(0).mineBlock(difficulty);
		
		Block secondBlock =new Block("Yo im the second block",blockchain.get(blockchain.size()-1).hash); 
		blockchain.add(secondBlock);
		logger.info("Trying to Mine block 2... ");
		blockchain.get(1).mineBlock(difficulty);
		
		blockchain.add(new Block("Hey im the third block",blockchain.get(blockchain.size()-1).hash));
		logger.info("Trying to Mine block 3... ");
		blockchain.get(2).mineBlock(difficulty);	
		
		blockchain.add(new Block("Hey im the fourth block",blockchain.get(blockchain.size()-1).hash));
		logger.info("Trying to Mine block 4... ");
		blockchain.get(3).mineBlock(difficulty);
		
		logger.info("\nBlockchain is Valid: " + isChainValid());
		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		logger.info("\nThe block chain: ");
		logger.info(blockchainJson);
		
		secondBlock.data="This block is Hacked";
		blockchain.set(1, secondBlock);
		blockchain.get(1).mineBlock(difficulty);
		logger.info("\nBlockchain is Valid: " + isChainValid());
		
	}
	
	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		//loop through blockchain to check hashes:
		for(int i=1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				logger.info("Current Hashes not equal");			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				logger.info("Previous Hashes not equal");
				return false;
			}
			//check if hash is solved
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
				logger.info("This block hasn't been mined");
				return false;
			}
		}
		return true;
	}
}

