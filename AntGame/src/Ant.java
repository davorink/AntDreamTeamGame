import java.awt.Point;

/*
 * A class to represent an ant
 * @author K Hutchings
 * @version 31/03/14
 */

public class Ant {
	private int id;
	private String colour;
	private int state;
	private int resting;
	private int direction;
	private boolean hasFood;
	
	/*
	 * Create the ant object
	 * @param ID The ID of the ant
	 * @param colour The ants colour
	 * @param food The amount of food particles the ant is carrying
	 */
	
	//*What is the default starting direction?
	public Ant(int id, String colour) {
		this.id = id;
		this.colour = colour;
		this.state = 0;
		this.resting = 0;
		this.hasFood = false
		this.direction = 0;
	}
	
	/*
	 * Turn an ant one place left or right
	 * @param lr specifying L or R
	 */
	public void turn(String lr) {
		if (lr.equals("L")) {
			this.direction = (direction + 5) % 6;
		}
		else {
			this.direction = (direction + 1) % 6;
		}
	}
			
	//Setters and getters for the ant's attributes
	public void setResting(int resting) {
		this.resting = resting;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public void setHasFood(boolean hasFood) {
		this.hasFood = hasFood;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public String getColour() {
		return this.colour;
	}
	
	public int getState() {
		return this.state;
	}
	
	public int getResting() {
		return this.resting;
	}
	
	public int getDirection() {
		return this.direction;
	}
	
	public boolean getHasFood() {
		return this.hasFood;
	}
	
}
