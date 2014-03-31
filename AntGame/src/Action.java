public class Action {
	
	public Action(String instruction, Ant ant)
	{
		doAction(instruction);
	} 
	public void doAction(String instruction, World world) {
		World world = GameEngine.getWorld(); //Get the world from the static getter method in the game engine class
		string action = instruction.substring(0, 2); //Read first two characters of instruction to determine which action it represents	
		String[] splitInstruction = instruction.split("\\s+"); //Split the elements of the instruction	
		switch (instruction) {
			case action.equals("Se"):
				senseDirection = splitInstruction[1]; //The direction to sense
				toCheck = splitInstruction[4]; //What to check that the sensed cell is
				pos antPostion = world.find_ant(ant.id); //Get the ants position
				pos sensePosition = senseCell(senseDirection, antPosition) //Get the postion of the cell to sense
				if ()
				break;
			case action.equals("Ma"):
				
				break;
			case action.equals("Un"):
				
				break;
			case action.equals("Pi"):
					
				break;
			case action.equals("Dr"):
				
				break;
			case action.equals("Tu"):
				
				break;
			case action.equals("Mo"):
				
				break;
			case action.equals("Fl"):
					
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
	
	public static pos senseCell(String halr, pos currentXY) {
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
	
}
