import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class to represent a brain engine (only one brain engine for each game (file path method used to change to different brain file)
 * @author K Hutchings
 * @version 03/04/14
 */

public class BrainEngine {
	private String filePath;
	private ArrayList<String> list;
	private int lineAmount;
	
	/**
	 * Creates a brain engine object with a link to the file the brain is in
	 * @param filePath The path of the brain file
	 */
	public BrainEngine(String filePath) throws FileNotFoundException, InvalidInstruction, TooManyInstructions {
		this.filePath = filePath;
		Scanner s = new Scanner(new File(filePath));
		int lineAmount = 0;
		while (s.hasNext()) { //Get the amount of instructions in the file
			lineAmount++;
		}
		parseBrain();
	}
	
	/**
	 * Parse the file into a list of brain instructions
	 */
	public void parseBrain() throws FileNotFoundException, InvalidInstruction, TooManyInstructions {
		Scanner s = new Scanner(new File(filePath));
		int lineAmount = 0;
		while (s.hasNext()) { //Get the amount of instructions in the file
			lineAmount++;
		}
		s = new Scanner(new File(filePath));
		this.list = new ArrayList<String>();
		while (s.hasNext()){ //Scan through the file
			String instruction = s.next();
			if (checkSyntaxCorrect(instruction)) { //Make sure the instruction is syntactically correct
				this.list.add(instruction.toLowerCase()); //Add it to the array list (in lowercase)
			}
			else {
				throw new InvalidInstruction("Invalid instruction!");
			}
		}
		if (list.size() > 10000 || list.size() == 0) { //Make sure brain not too big
			throw new TooManyInstructions("Too many instructions!");
		}
		s.close();
	}
	
	/**
	 * Change the file path for the brain engine object and parse a new brain
	 * @param newPath The new file path
	 */	
	public void setPath(String newPath) throws FileNotFoundException, InvalidInstruction, TooManyInstructions {
		this.filePath = newPath;
		parseBrain();
	}
	
	/**
	 * Test if the brain is syntactically correct
	 * @param instr A brain instruction
	 * @return True if correct, otherwise false
	 */
	public boolean checkSyntaxCorrect(String instr) {
		String instruction = instr.toLowerCase(); //Convert to lowercase
		String legalDirections = "here ahead leftahead rightahead";
		String legalConditions = "friend foe friendwithfood foeWithfood food rock marker foemarker home foehome";
		String legalTurns = "left right";
		boolean validInstruction = false;
		String[] instructionParts = instruction.split("\\s+"); //Split it into tokens by space
		String action = instructionParts[0]; //Check what the action is
		switch(action) {
			case("sense"):
				if (instructionParts.length >= 5) { //Make sure sense instruction is valid
					if (legalDirections.contains(instructionParts[1]) && legalConditions.contains(instructionParts[4]) //Make sure all relevant elements are correct
						&& Integer.parseInt(instructionParts[2]) >= 0 && Integer.parseInt(instructionParts[2]) < lineAmount
						&& Integer.parseInt(instructionParts[3]) >= 0 && Integer.parseInt(instructionParts[3]) < lineAmount) {
						if (instructionParts[4].equals("marker") && instructionParts.length >= 6 //If its a marker condition, check the marker no
							&& Integer.parseInt(instructionParts[5]) >= 0  && Integer.parseInt(instructionParts[5]) < 6) {
							validInstruction = true;
						}
						else {
							validInstruction = true;
						}
					}						
				}
				break;
			case("move"):
			case("pickup"):
				if (instructionParts.length >= 3) {  //Make sure sense move & pickup instructions are valid
					if (Integer.parseInt(instructionParts[1]) < lineAmount && Integer.parseInt(instructionParts[2]) < lineAmount) {
						validInstruction = true;
					}
				}
				break;
			case("mark"):
			case("unmark"):
				if  (instructionParts.length >= 3) {  //Make sure sense mark & unmark instructions are valid
					if (Integer.parseInt(instructionParts[1]) >= 0  && Integer.parseInt(instructionParts[1]) < 6
						&& Integer.parseInt(instructionParts[2]) >= 0 && Integer.parseInt(instructionParts[2]) < lineAmount){
						validInstruction = true;
					}
				}
				break;
			case("drop"):
				if  (instructionParts.length >= 2) { //Make sure drop instruction is valid
					if (Integer.parseInt(instructionParts[1]) < lineAmount) {
						validInstruction = true;
					}
				}
				break;
			case("turn"):
				if (instructionParts.length >= 3) { //Make sure turn instruction is valid
					if (legalTurns.contains(instructionParts[1]) && Integer.parseInt(instructionParts[2]) < lineAmount) {
						validInstruction = true;
					}
				}
				break;
			case("flip"):
				if (instructionParts.length >= 4) { //Make sure flip instruction is valid
					if (instructionParts[1].matches("\\d+") //Make sure the 2nd element contains at least one digit
						&& Integer.parseInt(instructionParts[2]) >= 0 && Integer.parseInt(instructionParts[2]) < lineAmount
						&& Integer.parseInt(instructionParts[3]) >= 0 && Integer.parseInt(instructionParts[3]) < lineAmount) {
						validInstruction = true;						
					}
				}
				break;
			default:
				break;
		}
		return validInstruction;
	}

	
	/**
	 * Return a list of the parsed brain instructions
	 * @return The list of brain instructions
	 */	
	public ArrayList<String> getBrainInstructions() {
		return list;
	}
	
}

/**
 * Class TooManyInstructions
 * 
 * @author K Hutchings
 * @version 03/04/14
 */
class TooManyInstructions extends Exception {
    public String msg;
    
    public TooManyInstructions(String _msg){
        super(_msg);
        msg = _msg;
    }

}

/**
 * Class InvalidInstruction
 * 
 * @author K Hutchings
 * @version 03/04/14
 */
class InvalidInstruction extends Exception {
    public String msg;
    
    public InvalidInstruction(String _msg){
        super(_msg);
        msg = _msg;
    }

}



