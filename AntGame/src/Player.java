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
	 * @throws PlayerException 
	 */
	public void setName(String name) throws PlayerException {
		if (name == null) {
			throw new PlayerException("Trying to set player's name to null");
		} else {
			this.name = name;
		}
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
	 * @throws PlayerException 
	 */
	public void setColor(TeamColor color) throws PlayerException {
		if (color == null) {
			throw new PlayerException("Trying to set color to null");
		} else {
			this.color = color;
		}
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
	 * @throws PlayerException 
	 * @throws Exception 
	 */
	public void addPoints(int amount) throws PlayerException {//throws Exception {
		if (amount < 0) {
			throw new PlayerException("Trying to add negative value");
		} else {
			this.pointCounter += amount;
		}
	}

}

/**
 * Class PlayerException is used to report exceptions in the Player
 * @author D Kopic
 */
@SuppressWarnings("serial")
class PlayerException extends Exception {
	public String msg;
    
    public PlayerException(String _msg){
        super(_msg);
        msg = _msg;
    }
}