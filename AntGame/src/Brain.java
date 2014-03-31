import java.util.ArrayList;

/*
 * A class to represent an ant brain
 * @author K Hutchings
 * @version 31/03/14
 */
public class Brain {
	private ArrayList<String> instructions;
	
	/*
	 * Create the brain object
	 * @param instructions The instructions for the brain
	 */
	public Brain(ArrayList<String> instructions) {
		this.instructions = instructions;
	}
	
	/*
	 * Return the instruction at the given state
	 * @return The next instruction
	 */
	public String getState(int state) {
		return instructions.get(state);
	}
	
}
