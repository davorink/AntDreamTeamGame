/**
 * A class to represent a player
 * @author K Ratusznik
 * @version 27/03/2014
 */

public class Player {
	private int id;
	private String color;
	
	/**
	 * Construct an object
	 * @param id Player's ID
	 * @param color Player's color
	 */
	public Player(int id, String color) {
		this.id = id;
		this.color = color;
	}

	// Setters and getters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
