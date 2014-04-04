import java.util.ArrayList;

/**
 * A class to represent a player
 * @author K Ratusznik & K Hutchings
 * @version 03/04/2014
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
	}
	
	/**
	 * Return the instruction at the given state
	 * @return The next instruction
	 */
	public String getState(int state) {
		return instructions.get(state);
	}

	/**
	 * Get name.
	 * @return player's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name.
	 * @param name player's new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get colour.
	 * @return player's colour
	 */
	public TeamColor getColor() {
		return color;
	}

	/**
	 * Set colour. 
	 * @param colour player's new colour
	 */
	public void setColor(TeamColor color) {
		this.color = color;
	}

	/**
	 * Get points
	 * @return number of points won
	 */
	public int getPoints() {
		return pointCounter;
	}
	
	/**
	 * Add points
	 * @param number of points to increment by
	 */
	public void addPoints(int amount) {
		this.pointCounter += amount;
	}

}
