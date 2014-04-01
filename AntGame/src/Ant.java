import java.awt.Point;

/**
 * A class to represent an ant
 * @author K Hutchings
 * @version 31/03/14
 */
public class Ant {
	private int id;
	private Color color;
	private int state;
	private int resting;
	private int direction;
	private boolean hasFood;
	private Brain brain;
	
	/**
	 * Create the ant object
	 * @param ID The ID of the ant
	 * @param colour The ants colour
	 * @param food The amount of food particles the ant is carrying
	 */
	
	public Ant(int ID, Color color) {
		this.ID = ID;
		this.color = color;
		this.brain = GameEngine.getBrain(color);
		this.state = 0;
		this.resting = 0;
		this.hasFood = false
		this.direction = 0;
	}
		
	/**
	 * Get the instruction from the ants brain at its current state
	 * @param instruction The instruction to act on
	 */
	private String getInstruction(int state) {
		return brain.getState(state);
	}
	
	/**
	 * Perform the action on the ant/cell
	 */
	public void doAction() {		
		String instruction = getInstruction(state); //Get the instruction from the ants brain at its current state
		World world = GameEngine.getWorld(); //Get the world from the static getter method in the game engine class
		pos antPostion = world.find_ant(ID); //Get the ants position
		String[] splitInstruction = instruction.split("\\s+"); //Split the elements of the instruction	
		string action = instruction.substring(0, 2); //Read first two characters of instruction to determine which action it represents	
		switch (action) {
			case action.equals("Se"):
				senseDirection = splitInstruction[1]; //The direction to sense
				pos sensePosition = senseCell(senseDirection, antPosition) //Get the postion of the cell to sense
				if (cell_matches(sensePosition, splitInstruction[4], Color())) { //See if the sensed position matches what is to be checked
					setState(splitInstruction[2]); //Set the state to 1st supplied state number
				}
				else {
					setState(splitInstruction[3]); //Otherwise set the state to 2nd supplied state number
				}
				break;
			case action.equals("Ma"):
				world.set_marker_at(antPostion, color, splitInstruction[1]); //Set the specified marker at the current ant cell
				break;
			case action.equals("Un"):
				world.clear_marker_at(antPostion, color, splitInstruction[1]); //Clear the specified marker at the current ant cell		
			}				
				break;
			case action.equals("Pi"):
				if (!hasFood && world.foot_at(antPostion) > 0) { //Make sure the ant has not already got food and theres food at the ants current position
					world.set_food_at(antPosition, world.foot_at(antPostion)-1); //Reduce the food amount in the cell
					setHasFood = true; //The ant now has food
					setState = splitInstruction[1]; //Set the state to 1st supplied state number
				}
				else {
					setState = splitInstruction[2]; //Otherwise set the state to 2nd supplied state number
				}					
				break;
			case action.equals("Dr"):
				if (hasFood) { //Make sure the ant has food
					world.set_food_at(antPosition, world.foot_at(antPostion)+1); //Increase the food in the current ant position
				}
				setState = splitInstruction[1]; //Set the state to the supplied state number
				break;
			case action.equals("Tu"):
				setDirection(turn(direction, splitInstruction[1])); //Turn the ant in the direction specified
				setState = splitInstruction[2]; //Set the state to the supplied state number
				break;
			case action.equals("Mo"):
				if (!some_ant_is_at(adjacentCell(antPosition, direction))) { //See if an ant is ahead in the direction the ant is facing
					world.set_ant_at(adjacentCell(antPosition, direction)) //Set the ant at the new position
					world.clear_ant_at(antPosition) //Remove the ant from the current position
					setState(splitInstruction[1]); //Set the state to 1st supplied state number
				}
				else {
					setState(splitInstruction[2]); //Otherwise set the state to 2nd supplied state number
				}
				break;
			case action.equals("Fl"):
				randomNumber = gameEngine.randomInt(splitInstruction[1]); //Get the random number from the value supplied
				if (randomNumber == 0) {
					setState(splitInstruction[2]); //Set the state to 1st supplied state number
				}
				else {
					setState(splitInstruction[3]); //Otherwise set the state to 2nd supplied state number
				}
				break;
			default:
				System.out.println("Invalid instruction")
				break;		
		}
	}
	
	/**
	 * Sense cells either current, ahead, left or right
	 * @param halr the position to sense
	 * @param currentXY The current position the ant is on
	 * @return The position of the cell sensed
	 */	
	private static pos senseCell(String halr, pos currentXY) {
		pos sensedXY;
		if (halr.equals("Here") {
			return currentXY;
		}
		else if (halr.equals("Ahead") {			
			return World.adjacentCell(currentXY, direction);
		}
		else if (halr.equals("LeftAhead") {
			int senseDirection = (direction + 5) % 6;
			return World.adjacentCell(currentXY, senseDirection);
		}
		else {
			int senseDirection = (direction + 1) % 6;
			return World.adjacentCell(currentXY, senseDirection);
		}
	}
	
	/**
	 * Turn an ant one place left or right
	 * @param lr specifying L or R
	 * @return The direction turned
	 */
	private static int turn(int direction, String lr) {
		if (lr.equals("Left")) {
			return (direction + 5) % 6;
		}
		else {
			return (direction + 1) % 6;
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
	
	public void setState(int state) {
		this.state = state;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public Color getColor() {
		return this.color;
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
	
	public int getState() {
		return this.state;
	}
	
}
