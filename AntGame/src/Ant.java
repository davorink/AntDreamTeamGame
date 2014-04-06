import java.io.IOException;
import java.util.ArrayList;


/**
 * A class to represent an ant
 * @author K Hutchings
 * @version 03/04/14
 */

public class Ant {
	private int ID;
	private TeamColor color;
	private int state;
	private int resting;
	private int direction;
	private boolean hasFood;
	private Player player;
	
	/**
	 * Create the ant object
	 * @param ID The ID of the ant
	 * @param colour The ants colour
	 * @param food The amount of food particles the ant is carrying
	 */
	
	public Ant(int ID, TeamColor color) {
		this.ID = ID;
		this.color = color;
		//This was taken out for unit testing/debugging it needs to be put back in!!!
		//Also the constructor needs to have the Player taken out!!!
		
		this.player = GameEngine.getPlayer(color);
		this.state = 0;
		this.resting = 0;
		this.hasFood = false;
		this.direction = 0;
	}
		
	/**
	 * Get the instruction from the ants brain in the player class object at its current state
	 * @param instruction The instruction to act on
	 */
	public String getInstruction(int state) {
		return player.getState(state);
	}
	
	/**
	 * Perform the action on the ant/cell
	 */
	public void doAction() {		
		String instruction = getInstruction(state); //Get the instruction from the ants brain at its current state
		World world = GameEngine.getWorld(); //Get the world from the static getter method in the game engine class
		Pos antPosition = world.findAnt(ID); //Get the ants position
		String[] splitInstruction = instruction.split("\\s+"); //Split the elements of the instruction	
		String action = instruction.substring(0, 2); //Read first two characters of instruction to determine which action it represents
		if (resting > 0) { //If the ant is resting reduce its resting time by one, stay at the current state and ignore the action
			resting--;
		}
		else {
			if (action.equals("se")) {
				String senseDirection = splitInstruction[1]; //The direction to sense
				Pos sensePosition = senseCell(senseDirection, antPosition, direction, world); //Get the postion of the cell to sense
				if (world.cellMatches(sensePosition, splitInstruction[4], color)) { //See if the sensed position matches what is to be checked
					state = Integer.parseInt(splitInstruction[2]); //Set the state to 1st supplied state number
				}
				else {
					state = Integer.parseInt(splitInstruction[3]); //Otherwise set the state to 2nd supplied state number
				}
			}
			else if (action.equals("ma")) {
				world.setMarkerAt(antPosition, color, Integer.parseInt(splitInstruction[1])); //Set the specified marker at the current ant cell
			}
			else if (action.equals("un")) {
				world.clearMarkerAt(antPosition, color, Integer.parseInt(splitInstruction[1])); //Clear the specified marker at the current ant cell
			}
			else if (action.equals("pi")) {
				if (!hasFood && world.foodAt(antPosition) > 0) { //Make sure the ant has not already got food and theres food at the ants current position
					world.setFoodAt(antPosition, world.foodAt(antPosition)-1); //Reduce the food amount in the cell
					hasFood = true; //The ant now has food
					state = Integer.parseInt(splitInstruction[1]); //Set the state to 1st supplied state number
				}
				else {
					state = Integer.parseInt(splitInstruction[2]); //Otherwise set the state to 2nd supplied state number
				}
			}
			else if (action.equals("dr")) {
				if (hasFood) { //Make sure the ant has food
					world.setFoodAt(antPosition, world.foodAt(antPosition)+1); //Increase the food in the current ant position
					if (world.anthillColorAt(antPosition) != null) { //If the ant is dropping food on an ant hill
						//The method below is not yet available in GameEngine
						GameEngine.incClaimedFood(color, 1); //Increment the food for the ant hill teams colour
					}	
				}
				state = Integer.parseInt(splitInstruction[1]); //Set the state to the supplied state number
			}
			else if (action.equals("tu")) {
				direction = turn(direction, splitInstruction[1]); //Turn the ant in the direction specified
				state = Integer.parseInt(splitInstruction[2]); //Set the state to the supplied state number
			}
			else if (action.equals("mo")) {
				if (!world.someAntIsAt(world.adjacentCell(antPosition, direction)) //See if an ant is ahead in the direction the ant is facing
					&& !world.rocky(world.adjacentCell(antPosition, direction))) { //Also make sure ahead is not rocky
					world.setAntAt(world.adjacentCell(antPosition, direction), this); //Set the ant at the new position
					world.clearAntAt(antPosition); //Remove the ant from the current position
					state = Integer.parseInt(splitInstruction[1]); //Set the state to 1st supplied state number
				}
				else {
					state = Integer.parseInt(splitInstruction[2]); //Otherwise set the state to 2nd supplied state number
				}
			}
			else if (action.equals("fl")) {
				int randomNumber = GameEngine.randomInt(Integer.parseInt(splitInstruction[1])); //Get the random number from the value supplied
				if (randomNumber == 0) {
					state = Integer.parseInt(splitInstruction[2]); //Set the state to 1st supplied state number
				}
				else {
					state = Integer.parseInt(splitInstruction[3]); //Otherwise set the state to 2nd supplied state number
				}
			}
			world.checkForSurroundedAntAt(antPosition); //Check if the and is now surrounded
			resting = 15; //Set the resting to 15, now a move has been taken
		}
	}
	
	/**
	 * Sense cells either current, ahead, left or right
	 * @param halr the position to sense
	 * @param currentXY The current position the ant is on
	 * @return The position of the cell sensed
	 */	
	private static Pos senseCell(String halr, Pos currentXY, int direction, World world) {
		if (halr.equals("here")) {
			return currentXY;
		}
		else if (halr.equals("ahead")) {			
			return world.adjacentCell(currentXY, direction);
		}
		else if (halr.equals("leftahead")) {
			int senseDirection = (direction + 5) % 6;
			return world.adjacentCell(currentXY, senseDirection);
		}
		else {
			int senseDirection = (direction + 1) % 6;
			return world.adjacentCell(currentXY, senseDirection);
		}
	}
	
	/**
	 * Turn ant one place left or right
	 * @param lr specifying L or R
	 * @return The direction turned
	 */
	private static int turn(int direction, String lr) {
		if (lr.equals("left")) {
			return (direction + 5) % 6;
		}
		else {
			return (direction + 1) % 6;
		}
	}
	
	//Setters and getters for the ant's attributes	
	/**
	 * Set the resting
	 * @param resting
	 */
	public void setResting(int resting) {
		this.resting = resting;
	}
	
	/**
	 * Set the direction
	 * @param direction
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	/**
	 * Set has food condition
	 * @param hasFood
	 */
	public void setHasFood(boolean hasFood) {
		this.hasFood = hasFood;
	}
	
	/**
	 * Set the state
	 * @param state
	 */
	public void setState(int state) {
		this.state = state;
	}
	
	/**
	 * Get the ID
	 * @return The ants ID
	 */
	public int getID() {
		return this.ID;
	}
	
	/**
	 * Get the colour
	 * @return The ants team colour
	 */	
	public TeamColor getColor() {
		return this.color;
	}
	
	/**
	 * Get the state
	 * @return The ants state
	 */
	public int getState() {
		return this.state;
	}
	
	/**
	 * Get the resting time
	 * @return The ants resting time
	 */
	public int getResting() {
		return this.resting;
	}
	
	/**
	 * Get the direction
	 * @return The ants direction
	 */
	public int getDirection() {
		return this.direction;
	}
	
	/**
	 * Get the has food condition
	 * @return The ants has food condition
	 */
	public boolean getHasFood() {
		return this.hasFood;
	}
	
}
