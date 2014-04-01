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
	 * Test if the brain is syntactically correct
	 * @param list A list of brain instructions
	 * @return True if correct, otherwise false
	 */
	
	//*Need to check that the rest of the instruction is also valid
	//*When creating brain from file to array - need to take out any lines with just spaces
	public boolean checkBrainSyntax(ArrayList<String> list) {
		String legalTokens = "Sense Mark Unmark PickUp Drop Turn Move Flip";
		stringlegalDirections = "Here Ahead LeftAhead RightAhead";
		boolean validInstructions = false;
		
		for (int i=0; i<list.size(); i++) {
			String line = list.get(i); //Check each instruction
			String[] parts = line.split("\\s+"); //Split it into tokens by space
			String Action = parts[0];
			switch(action) {
				case action.equals("Sense"):
					if (parts.length == 5 && legalTokens.contains(parts[1] && parts.contains(parts[4])
							&& (parts[2] >= 0 && parts[2] < )
							) {
						
					}
					break;
					
							
					
					
					
					validInstructions = true;
				}
			}				
		}
		return validInstructions;
	}
	
	/*
	 * Return a list of the parsed brain instructions
	 * @return The list of brain instructions
	 */	
	public ArrayList<String> getBrainInstructions() {
		return list;
	}
	
}

