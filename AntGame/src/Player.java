import java.util.ArrayList;

/**
 * A class to represent a player
 * @author K Ratusznik
 * @version 01/04/2014
 */

public class Player {
	private String name;
	private color color;
	private int winCounter;
	private ArrayList<String> instructions;
	
	/**
	 * Construct an object
	 * @param name Player's name
	 * @param color Player's color
	 */
	public Player(String name, color color, ArrayList<String> instructions) {
		this.name = name;
		this.color = color;
		this.instructions = instructions;
		winCounter = 0;
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
	public color getColour() {
		return color;
	}

	/**
	 * Set colour. 
	 * @param colour player's new colour
	 */
	public void setColor(color color) {
		this.color = color;
	}

	/**
	 * Get win count.
	 * @return number of won games
	 */
	public int getWinCounter() {
		return winCounter;
	}
	
	/**
	 * Return the instruction at the given state
	 * @return The next instruction
	 */
	public String getState(int state) {
		return instructions.get(state);
	}
}
