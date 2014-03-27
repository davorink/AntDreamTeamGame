import java.awt.Point;

/*
 * A class to represent an ant
 * @author K Hutchings
 * @version 26/03/14
 */

public class Ant {
	private int id;
	private int brainID;
	public enum Colour {RED, BLACK}
	private Colour colour;
	public enum LR {L, R}
	private LR lr;
	public enum HALR {H, A, L, R}
	private HALR halr;
	private int state;
	private int resting;
	private int direction;
	private boolean hasFood;
	
	/*
	 * Create the ant object
	 * @param brainID The ID of the ants brain
	 * @param brain The ants brain
	 * @param colour The ants colour
	 * @param state The ants state
	 * @param resting If the ant is resting
	 * @param direction The direction of the ant
	 * @param food The amount of food particles the ant is carrying
	 */
	
	//*What is the default starting direction?
	public Ant(int id, int brainID, Colour colour, int direction) {
		this.id = id;
		this.brainID = brainID;
		this.colour = colour;
		this.state = 0;
		this.resting = 0;
		this.direction = direction;
		this.hasFood = false;
	}
	
	/*
	 * Move the ant onto the next state and reduce any resting time
	 */
	public void nextState() {
		this.state++;
		if (this.resting != 0) {
			this.resting--;
		}
	}
	
	/*
	 * Sense cells either current, ahead, left or right
	 * @param the halr enum to represent position to sense
	 * @param currentXY The current position the ant is on
	 * @return The position of the cell sensed
	 */
	
	//*The method ajdacentCell is part of the World class*
	public Point senseCell(HALR halr, Point currentXY) {
		Point sensedXY;
		if (halr.toString() == "H") {
			return currentXY;
		}
		else if (halr.toString() == "A") {			
			return World.adjacentCell(currentXY, direction);
		}
		else if (halr.toString() == "L") {
			int senseDirection = (direction + 5) % 6;
			return World.adjacentCell(currentXY, senseDirection);
		}
		else {
			int senseDirection = (direction + 1) % 6;
			return World.adjacentCell(currentXY, senseDirection);
		}
	}
	
	/*
	 * Turn an ant one place left or right
	 * @param lr enum specifying L or R
	 */
	public void turn(LR lr) {
		if (lr.toString() == "L") {
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
		return this.id;
	}
	
	public int getBrainID() {
		return this.brainID;
	}
	
	public Colour getColour() {
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
