/**
 * A class to represent a player
 * @author K Ratusznik
 * @version 31/03/2014
 */

public class Player {
	private String name;
	private String color;
	
	/**
	 * Construct an object
	 * @param name Player's name
	 * @param color Player's color
	 */
	public Player(String name, String color) {
		this.name = name;
		this.color = color;
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
	public Colour getColour() {
		return colour;
	}

	/**
	 * Set colour. 
	 * @param colour player's new colour
	 */
	public void setColor(Colour colour) {
		this.colour = colour;
	}
}
