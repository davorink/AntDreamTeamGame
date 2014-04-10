
import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Test;

public class BrainEngineTest {
	
	@Test
	public void testBrainEngine() throws InvalidInstruction, InvalidInstructionsSet, IOException{
		assertNotNull("Null object", new BrainEngine());
	}
	
	@Test
	public void testParseBrain() throws InvalidInstruction, InvalidInstructionsSet, IOException{
		BrainEngine brainEngine = new BrainEngine();
		brainEngine.setPath("n:/Brains/solution-1.brain");
		assertNotNull("Null object", brainEngine.getBrainInstructions());
	}
	
	@Test
	public void testCheckSyntaxCorrect() throws InvalidInstruction, InvalidInstructionsSet, IOException{
		boolean validBrain = true;
		BrainEngine brainEngine = new BrainEngine();
		brainEngine.setPath("n:/Brains/solution-1.brain");
		ArrayList<String> brain = brainEngine.getBrainInstructions();
		for (int i=0; i<brain.size(); i++) {
			if (!brainEngine.checkSyntaxCorrect(brain.get(i))) {
				validBrain = false;
			}
		}
		assertTrue("Invalid brain", validBrain);
	}
	
	@Test (expected = IOException.class)
	public void testSetPath() throws InvalidInstruction, InvalidInstructionsSet, IOException{
		BrainEngine brainEngine = new BrainEngine();
		brainEngine.setPath("???");
		
	}

}
