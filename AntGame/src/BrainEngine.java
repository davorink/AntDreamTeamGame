
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

/**
 * A class to represent a brain engine (only one brain engine for each game (file path method used to change to different brain file)
 * @author K Hutchings
 * @version 07/04/14
 */

public class BrainEngine {
	private String filePath;
	private ArrayList<String> list;
	private int lineAmount;
	
	/**
	 * Creates a brain engine object
	 * @throws IOException 
	 */
	public BrainEngine() throws InvalidInstruction, InvalidInstructionsSet, IOException {
	}
	
	/**
	 * Parse the file into a list of brain instructions
	 * @throws IOException 
	 */
	public void parseBrain() throws InvalidInstruction, InvalidInstructionsSet, IOException {
		LineNumberReader reader  = new LineNumberReader(new FileReader(filePath));
		this.list = new ArrayList<String>();
		String instruction = "";
		int i = 0;
		while ((instruction = reader.readLine()) != null) { //Scan through the file
			if (checkSyntaxCorrect(instruction)) { //Make sure the instruction is syntactically correct
				this.list.add(instruction.toLowerCase()); //Add it to the array list (in lower case)
				i++;
			}
			else {
				reader.close();
				System.out.println("Error with instruction no: " + i);
				throw new InvalidInstruction("Invalid instruction!");
			}
		}
		reader.close();
		if (list.size() > 10000 || list.size() == 0) { //Make sure brain not too big
			throw new InvalidInstructionsSet("Invalid instruction set!");
		}
	}
	
	/**
	 * Change the file path for the brain engine object and parse a new brain
	 * @param newPath The new file path
	 * @throws IOException 
	 */	
	public void setPath(String filePath) throws InvalidInstruction, InvalidInstructionsSet, IOException {
		this.filePath = filePath;
		LineNumberReader reader  = new LineNumberReader(new FileReader(filePath));
		lineAmount = 0;
		String lineRead = "";
		while ((lineRead = reader.readLine()) != null) {} //Get the amount of instructions in the file
		lineAmount = reader.getLineNumber();
		reader.close();
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
		String legalConditions = "friend foe friendwithfood foewithfood food rock marker foemarker home foehome";
		String legalTurns = "left right";
		boolean validInstruction = false;
		String[] instructionParts = instruction.split("\\s+"); //Split it into tokens by space
		String action = instructionParts[0]; //Check what the action is
		if (action.equals("sense")) {
			if (instructionParts.length >= 5) { //Make sure sense instruction is valid
				if (legalDirections.contains(instructionParts[1]) && legalConditions.contains(instructionParts[4]) //Make sure all relevant elements are correct
					&& Integer.parseInt(instructionParts[2]) >= 0 && Integer.parseInt(instructionParts[2]) < lineAmount
					&& Integer.parseInt(instructionParts[3]) >= 0 && Integer.parseInt(instructionParts[3]) < lineAmount) {
					validInstruction = true;
				}
				if (instructionParts[4].equals("marker")) { //If its a marker condition, check the marker no
					if (instructionParts.length >= 6 && Integer.parseInt(instructionParts[5]) >= 0  
						&& Integer.parseInt(instructionParts[5]) < 6) {
						validInstruction = true;
					}
					else {
						validInstruction = false;
					}
				}						
			}
		}
		else if (action.equals("move") || action.equals("pickup")) {
			if (instructionParts.length >= 3) {  //Make sure sense move & pickup instructions are valid
				if (Integer.parseInt(instructionParts[1]) < lineAmount && Integer.parseInt(instructionParts[2]) < lineAmount) {
					validInstruction = true;
				}
			}
		}
		else if (action.equals("mark") || action.equals("unmark")) {
			if  (instructionParts.length >= 3) {  //Make sure sense mark & unmark instructions are valid
				if (Integer.parseInt(instructionParts[1]) >= 0  && Integer.parseInt(instructionParts[1]) < 6
					&& Integer.parseInt(instructionParts[2]) >= 0 && Integer.parseInt(instructionParts[2]) < lineAmount){
					validInstruction = true;
				}
			}
		}
		else if (action.equals("drop")) {
			if  (instructionParts.length >= 2) { //Make sure drop instruction is valid
				if (Integer.parseInt(instructionParts[1]) < lineAmount) {
					validInstruction = true;
				}
			}
		}
		else if (action.equals("turn")) {
			if (instructionParts.length >= 3) { //Make sure turn instruction is valid
				if (legalTurns.contains(instructionParts[1]) && Integer.parseInt(instructionParts[2]) < lineAmount) {
					validInstruction = true;
				}
			}
		}
		else if (action.equals("flip")) {
			if (instructionParts.length >= 4) { //Make sure flip instruction is valid
				if (instructionParts[1].matches("\\d+") //Make sure the 2nd element contains at least one digit
					&& Integer.parseInt(instructionParts[2]) >= 0 && Integer.parseInt(instructionParts[2]) < lineAmount
					&& Integer.parseInt(instructionParts[3]) >= 0 && Integer.parseInt(instructionParts[3]) < lineAmount) {
					validInstruction = true;						
				}
			}
		}
		if (!validInstruction) {
			System.out.println("Invalid instruction: " + instruction);
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
 * Class InvalidInstructionsSet
 * 
 * @author K Hutchings
 * @version 03/04/14
 */
class InvalidInstructionsSet extends Exception {
    public String msg;
    
    public InvalidInstructionsSet(String _msg){
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



