
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class AntTest {

	@Test
	public void testAnt() throws InvalidInstruction, InvalidInstructionsSet, IOException, InvalidValueException {
		BrainEngine brainEngine = new BrainEngine();
		brainEngine.setPath("n:/Brains/solution-1.brain");
		ArrayList<String> brain = brainEngine.getBrainInstructions();
		Player player = new Player("Kerry", brain);
		assertNotNull("Null object", new Ant(15, TeamColor.RED));
	}
	
	@Test (expected = InvalidValueException.class)
	public void testAnt1() throws InvalidInstruction, InvalidInstructionsSet, IOException, InvalidValueException {
		BrainEngine brainEngine = new BrainEngine();
		brainEngine.setPath("n:/Brains/solution-1.brain");
		ArrayList<String> brain = brainEngine.getBrainInstructions();
		Player player = new Player("Kerry", brain);
		Ant ant = new Ant(-1, TeamColor.RED);
	}

	@Test
	public void testGetInstruction() throws InvalidInstruction, InvalidInstructionsSet, IOException, InvalidValueException {
		BrainEngine brainEngine = new BrainEngine();
		brainEngine.setPath("n:/Brains/solution-1.brain");
		ArrayList<String> brain = brainEngine.getBrainInstructions();
		Player player = new Player("Kerry", brain);
		Ant ant = new Ant(250, TeamColor.BLACK);
		assertTrue("Incorrect instruction returned: " + ant.getInstruction(0), ant.getInstruction(0).equals("flip 2 243 243"));
		assertTrue("Incorrect instruction returned: " + ant.getInstruction(8003), ant.getInstruction(8003).equals("flip 2 8003 8003"));
	}

	@Test
	public void testDoAction() throws InvalidInstruction, InvalidInstructionsSet, IOException, InvalidValueException {
        BrainEngine brainEngine = new BrainEngine(); 
        brainEngine.setPath("n:/Brains/solution-1.brain");
		ArrayList<String> brain = brainEngine.getBrainInstructions();
		Player player = new Player("Kerry", brain);
		Ant ant = new Ant(0, TeamColor.BLACK);
		String instruction = ant.getInstruction(1);
		String[] splitInstruction = instruction.split("\\s+");				
		assertTrue("Invalid 'sense' action parts", splitInstruction[0].equals("sense") && splitInstruction[1].equals("ahead")
		&& splitInstruction[2].equals("75") && splitInstruction[3].equals("2") && splitInstruction[4].equals("home"));
		splitInstruction = ant.getInstruction(22).split("\\s+");
		assertTrue("Invalid 'mark' action parts: ", splitInstruction[0].equals("mark") && splitInstruction[1].equals("1")
		&& splitInstruction[2].equals("138"));
		splitInstruction = ant.getInstruction(21).split("\\s+");
		assertTrue("Invalid 'unmark' action parts", splitInstruction[0].equals("unmark") && splitInstruction[1].equals("0")
		&& splitInstruction[2].equals("22"));
		splitInstruction = ant.getInstruction(245).split("\\s+");
		assertTrue("Invalid 'pickup' action parts", splitInstruction[0].equals("pickup") && splitInstruction[1].equals("913")
		&& splitInstruction[2].equals("344"));
		splitInstruction = ant.getInstruction(247).split("\\s+");
		assertTrue("Invalid 'drop' action parts", splitInstruction[0].equals("drop") && splitInstruction[1].equals("243"));
		splitInstruction = ant.getInstruction(259).split("\\s+");
		
		assertTrue("Invalid 'turn' action parts", splitInstruction[0].equals("turn") && splitInstruction[1].equals("right")
		&& splitInstruction[2].equals("1594"));
		splitInstruction = ant.getInstruction(275).split("\\s+");
		assertTrue("Invalid 'move' action parts", splitInstruction[0].equals("move") && splitInstruction[1].equals("276")
		&& splitInstruction[2].equals("274"));
		splitInstruction = ant.getInstruction(0).split("\\s+");
		assertTrue("Invalid 'flip' action parts", splitInstruction[0].equals("flip") && splitInstruction[1].equals("2")
		&& splitInstruction[2].equals("243") && splitInstruction[3].equals("243"));	
	}

	@Test (expected = InvalidValueException.class)
	public void testSetResting() throws InvalidInstruction, InvalidInstructionsSet, IOException, InvalidValueException {
		BrainEngine brainEngine = new BrainEngine(); 
        brainEngine.setPath("n:/Brains/solution-1.brain");
		ArrayList<String> brain = brainEngine.getBrainInstructions();
		Player player = new Player("Kerry", brain);
		Ant ant = new Ant(0, TeamColor.BLACK);
		ant.setResting(10);
	}

	@Test (expected = InvalidValueException.class)
	public void testSetDirection() throws InvalidInstruction, InvalidInstructionsSet, IOException, InvalidValueException {
		BrainEngine brainEngine = new BrainEngine(); 
        brainEngine.setPath("n:/Brains/solution-1.brain");
		ArrayList<String> brain = brainEngine.getBrainInstructions();
		Player player = new Player("Kerry", brain);
		Ant ant = new Ant(0, TeamColor.BLACK);
		ant.setDirection(25);
	}

	@Test 
	public void testSetHasFood()  throws InvalidInstruction, InvalidInstructionsSet, IOException, InvalidValueException {
		BrainEngine brainEngine = new BrainEngine(); 
        brainEngine.setPath("n:/Brains/solution-1.brain");
		ArrayList<String> brain = brainEngine.getBrainInstructions();
		Player player = new Player("Kerry", brain);
		Ant ant = new Ant(0, TeamColor.BLACK);
		ant.setHasFood(true);
		assertTrue("Ant has no food", ant.getHasFood());
	}

	@Test (expected = InvalidValueException.class)
	public void testSetState()  throws InvalidInstruction, InvalidInstructionsSet, IOException, InvalidValueException {
		BrainEngine brainEngine = new BrainEngine(); 
        brainEngine.setPath("n:/Brains/solution-1.brain");
		ArrayList<String> brain = brainEngine.getBrainInstructions();
		Player player = new Player("Kerry", brain);
		Ant ant = new Ant(0, TeamColor.BLACK);
		ant.setState(-1);
	}

	@Test
	public void testGetID()  throws InvalidInstruction, InvalidInstructionsSet, IOException, InvalidValueException {
		BrainEngine brainEngine = new BrainEngine(); 
        brainEngine.setPath("n:/Brains/solution-1.brain");
		ArrayList<String> brain = brainEngine.getBrainInstructions();
		Player player = new Player("Kerry", brain);
		Ant ant = new Ant(0, TeamColor.BLACK);
		assertTrue("Incorrect id", ant.getID() == 0);
	}

	
	@Test
	public void testGetColor()  throws InvalidInstruction, InvalidInstructionsSet, IOException, InvalidValueException {
		BrainEngine brainEngine = new BrainEngine(); 
        brainEngine.setPath("n:/Brains/solution-1.brain");
		ArrayList<String> brain = brainEngine.getBrainInstructions();
		Player player = new Player("Kerry", brain);
		Ant ant = new Ant(0, TeamColor.BLACK);
		assertTrue("Incorrect colour", ant.getColor() == TeamColor.BLACK);
	}

	@Test
	public void testGetState()  throws InvalidInstruction, InvalidInstructionsSet, IOException, InvalidValueException {
		BrainEngine brainEngine = new BrainEngine(); 
        brainEngine.setPath("n:/Brains/solution-1.brain");
		ArrayList<String> brain = brainEngine.getBrainInstructions();
		Player player = new Player("Kerry", brain);
		Ant ant = new Ant(0, TeamColor.BLACK);
		assertTrue("Incorrect state", ant.getState() == 0);
	}

	@Test
	public void testGetResting()  throws InvalidInstruction, InvalidInstructionsSet, IOException, InvalidValueException {
		BrainEngine brainEngine = new BrainEngine(); 
        brainEngine.setPath("n:/Brains/solution-1.brain");
		ArrayList<String> brain = brainEngine.getBrainInstructions();
		Player player = new Player("Kerry", brain);
		Ant ant = new Ant(0, TeamColor.BLACK);
		assertTrue("Incorrect resting", ant.getResting() == 0);
	}

	@Test
	public void testGetDirection()  throws InvalidInstruction, InvalidInstructionsSet, IOException, InvalidValueException {
		BrainEngine brainEngine = new BrainEngine();
        brainEngine.setPath("n:/Brains/solution-1.brain");
		ArrayList<String> brain = brainEngine.getBrainInstructions();
		Player player = new Player("Kerry", brain);
		Ant ant = new Ant(0, TeamColor.BLACK);
		assertTrue("Incorrect direction", ant.getDirection() == 0);
	}

}
