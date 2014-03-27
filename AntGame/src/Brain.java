import java.util.ArrayList;

/*
 * A class to represent an ant brain
 * @author K Hutchings
 * @version 26/03/14
 */
public class Brain {
	private int id;
	private ArrayList<String> instructions;
	private int currentInstruction;
	
	/*
	 * Create the brain object
	 * @param instructions The instructions for the brain
	 * @param andID The ID of the ant the brain is for
	 */
	public Brain(ArrayList<String> instructions, int id) {
		this.id = id;
		this.instructions = instructions;
		this.currentInstruction = -1;
	}
	
	/*
	 * Return the next instruction
	 * @return The next instruction
	 */
	public String getNextInstruction() {
		currentInstruction ++;
		return instructions.get(currentInstruction);
	}
	
}
