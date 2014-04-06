import java.util.ArrayList;
/**
 * A class to represent a player
 * @author K Ratusznik & K Hutchings
 */

public class Player {
	private String name;
	private TeamColor color;
	private int pointCounter;
	private ArrayList<String> instructions;
	
	/**
	 * Construct an object
	 * @param name Player's name
	 * @param color Player's color
	 */
	public Player(String name, ArrayList<String> instructions) {
		this.name = name;
		this.color = null;
		this.instructions = instructions;
		pointCounter = 0;
		System.out.println("=========== Player Constructor ===========");
		System.out.println("name: " + name + " " + "color: " + color + " " + "instructions: " + instructions.size() + " " + "pointCounter: " + pointCounter);
	}
	
	/**
	 * Return the instruction at the given state
	 * @return The next instruction
	 */
	public String getState(int state) {
		System.out.println("===== getState Method - Player");
		System.out.println("state: " + state);
		return instructions.get(state);
	}

	/**
	 * Get name.
	 * @return player's name
	 */
	public String getName() {
		System.out.println("===== getName Method");
		System.out.println("name: " + name);
		return name;
	}

	/**
	 * Set name.
	 * @param name player's new name
	 */
	public void setName(String name) {
		System.out.println("===== setName Method");
		System.out.println("Old name: " + getName() );
		System.out.println("New name: " + name);
		this.name = name;
	}

	/**
	 * Get colour.
	 * @return player's colour
	 */
	public TeamColor getColor() {
		System.out.println("===== getColor Method");
		System.out.println("color: " + color);
		return color;
	}

	/**
	 * Set colour. 
	 * @param colour player's new colour
	 */
	public void setColor(TeamColor color) {
		System.out.println("===== setColor Method");
		System.out.println("Old color: " + getColor() );
		System.out.println("New color: " + color);
		this.color = color;
	}

	/**
	 * Get points
	 * @return number of points won
	 */
	public int getPoints() {
		System.out.println("===== getPoints Method");
		System.out.println("points: " + pointCounter);
		return pointCounter;
	}
	
	/**
	 * Add points
	 * @param number of points to increment by
	 */
	public void addPoints(int amount) {
		System.out.println("===== addPoints Method");
		System.out.println("current points: " + getPoints() + "add amount: " + amount);
		this.pointCounter += amount;
	}

}
