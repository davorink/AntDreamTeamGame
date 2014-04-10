import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The main class.
 * @author K King
 * @version 06/04/2014
 */
public class AntGame {
	
	static GUI gui;
	static GameEngine engine;

	public static void main(String[] args) throws CellException {
		// Initialise game engine.
		engine = new GameEngine();
		// Initialise GUI.
		gui = new GUI("Ant Wars");
		(new Thread(gui)).start();
		
		/*Player[] testPlayers = new Player[2];
		BrainEngine brainEngine = null;

		try {
			brainEngine = new BrainEngine();
			brainEngine.setPath("cleverbrain2.brain");
		} catch (InvalidInstruction e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidInstructionsSet e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		testPlayers[0] = new Player("Kieran", brainEngine.getBrainInstructions());
		testPlayers[1] = new Player("Kerry", brainEngine.getBrainInstructions());
		
		try {
			System.out.println("asfd");
			//AntGame.e.runOneVsOne(testPlayers, null);
		} catch (Exception e) {
		}*/
	}
}

/*import java.io.FileNotFoundException;
import java.io.IOException;

public class AntGame {

	private static GameEngine engine;
	
	public static void main(String[] args) {
		// Initialise game engine.
		//engine = new GameEngine();
		// Initialise GUI.
		GUI g = new GUI("Ant game", engine);
		(new Thread(g)).start();
		
		// moved to GUI.java
	}
}*/