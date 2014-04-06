import java.io.FileNotFoundException;

/**
 * The main class.
 * @author K King
 * @version 06/04/2014
 */
public class AntGame {

	private static GameEngine engine;
	
	public static void main(String[] args) {
		// Initialise game engine.
		engine = new GameEngine();
		// Initialise GUI.
		
		Player[] testPlayers = new Player[2];
		BrainEngine brainEngine = null;

		try {
			brainEngine = new BrainEngine("C:\\Users\\Kieran\\snakebrain.brain");
		} catch (FileNotFoundException e) {
		} catch (InvalidInstruction e) {
		} catch (TooManyInstructions e) {
		}

		System.out.println("rawr");
			
		testPlayers[0] = new Player("Kieran", brainEngine.getBrainInstructions());
		testPlayers[1] = new Player("Kerry", brainEngine.getBrainInstructions());
		
		try {
			engine.runOneVsOne(testPlayers, null);
		} catch (Exception e) {
		}
	}
}