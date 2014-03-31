public class Action {
	
	public Action(String instruction, Ant ant)
	{
		doAction(instruction);
	}
	
	public void doAction(String instruction, World world) {
		World world = GameEngine.getWorld(); //Get the world from the static getter method in the game engine class
		pos antPostion = world.find_ant(ant.id); //Get the ants position
		string action = instruction.substring(0, 2); //Read first two characters of instruction to determine which action it represents	
		String[] splitInstruction = instruction.split("\\s+"); //Split the elements of the instruction	
		switch (instruction) {
			case action.equals("Se"):
				senseDirection = splitInstruction[1]; //The direction to sense
				pos sensePosition = senseCell(senseDirection, antPosition) //Get the postion of the cell to sense
				if (cell_matches(sensePosition, splitInstruction[4], ant.getColor())) { //See if the sensed position matches what is to be checked
					ant.setState = splitInstruction[2]; //Set the state to 1st supplied state number
				}
				else {
					ant.setState = splitInstruction[3]; //Otherwise set the state to 2nd supplied state number
				}
				break;
			case action.equals("Ma"):
				world.set_marker_at(antPostion, ant.getColor, splitInstruction[1]); //Set the specified marker at the current ant cell
				break;
			case action.equals("Un"):
				world.clear_marker_at(antPostion, ant.getColor, int marker); //Clear the specified marker at the current ant cell		
			}				
				break;
			case action.equals("Pi"):
				if (!ant.hasFood && world.foot_at(antPostion) > 0) { //Make sure the ant has not already got food and theres food at the ants current position
					world.set_food_at(antPosition, world.foot_at(antPostion)-1); //Reduce the food amount in the cell
					ant.setHasFood = true; //The ant now has food
					ant.setState = splitInstruction[1]; //Set the state to 1st supplied state number
				}
				else {
					ant.setState = splitInstruction[2]; //Otherwise set the state to 2nd supplied state number
				}					
				break;
			case action.equals("Dr"):
				if (ant.hasFood) { //Make sure the ant has food
					world.set_food_at(antPosition, world.foot_at(antPostion)+1); //Increase the food in the current ant position
				}
				ant.setState = splitInstruction[1]; //Set the state to the supplied state number
				break;
			case action.equals("Tu"):
				ant.setDirection(turn(ant.getDirection(), splitInstruction[1])); //Turn the ant in the direction specified
				ant.setState = splitInstruction[2]; //Set the state to the supplied state number
				break;
			case action.equals("Mo"):
				senseDirection = splitInstruction[1]; //The direction to sense
				pos sensePosition = senseCell(senseDirection, antPosition) //Get the postion of the cell to sense
				if (!some_ant_is_at(adjacentCell(antPosition, ant.getDirection()))) { //See if an ant is ahead in the direction the ant is facing
					world.set_ant_at(adjacentCell(antPosition, ant.getDirection())) //Set the ant at the new position
					world.clear_ant_at(antPosition) //Remove the ant from the current position
					ant.setState = splitInstruction[1]; //Set the state to 1st supplied state number
				}
				else {
					ant.setState = splitInstruction[2]; //Otherwise set the state to 2nd supplied state number
				}
				break;
			case action.equals("Fl"):
				randomNumber = gameEngine.randomInt(splitInstruction[1]); //Get the random number from the value supplied
				if (randomNumber == 0) {
					ant.setState = splitInstruction[2]; //Set the state to 1st supplied state number
				}
				else {
					ant.setState = splitInstruction[3]; //Otherwise set the state to 2nd supplied state number
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
	
}
