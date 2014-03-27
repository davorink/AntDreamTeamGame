import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * A class to represent a brain engine (only one brain engine for each game (file path method used to change to different brain file)
 * @author K Hutchings
 * @version 26/03/14
 */
public class BrainEngine {
	private String filePath;
	private ArrayList<String> list;
	private int brainID;
	
	/*
	 * Creates a brain engine object with a link to the file the brain is in
	 * @param filePath The path of the brain file
	 */
	public BrainEngine(String filePath) throws FileNotFoundException {
		this.filePath = filePath;
		brainID = 0;
		parseBrain();
	}
	
	/*
	 * Parse the file into a list of brain instructions
	 */
	public void parseBrain() throws FileNotFoundException {
		Scanner s = new Scanner(new File(filePath));
		this.list = new ArrayList<String>();
		while (s.hasNext()){
		    this.list.add(s.next());
		}
		s.close();
	}
	
	/*
	 * Change the file path for the brain engine object and parse a new brain
	 * @param newPath The new file path
	 */	
	public void setPath(String newPath) throws FileNotFoundException {
		this.filePath = newPath;
		parseBrain();
	}
	
	/*
	 * Increment the current brain id
	 */
	public void incrementCurrentBrainID() {
		this.brainID++;
	}
	
	/*
	 * Return the current brain id
	 * @return The current brain id
	 */	
	public int getCurrentBrainID() {
		return this.brainID;
	}
	
	/*
	 * Test if the brain is syntactically correct
	 * @param list A list of brain instructions
	 * @return True if correct, otherwise false
	 */
	
	//*Need to check that the rest of the instruction is also valid
	public boolean checkBrainSyntax(ArrayList<String> list) {
		String[] legalTokens = {"Sense", "Mark", "Unmark", "PickUp", "Drop", "Turn", "Move", "Flip"};
		boolean validInstruction = false;
		for (int i=0; i<list.size(); i++) {
			String line = list.get(i); //Check each instruction
			String[] parts = line.split("\\s+"); //Split it into tokens by space
			for (int j=0; j<legalTokens.length; j++) { //Check if the starter instruction token is valid
				if (parts[0] == legalTokens[j]) {
					validInstruction = true;
				}
			}				
		}
		return validInstruction;
	}
	
	/*
	 * Return a list of the parsed brain instructions
	 * @return The list of brain instructions
	 */	
	public ArrayList<String> getBrainInstructions() {
		return list;
	}
	
}

