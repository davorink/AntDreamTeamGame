import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;


public class GameEngineTest {

	@Test
	public void testInitialiseTestGame() {
		// Initialise testing environment.
		GameEngine gameEngine = new GameEngine();
		try {
			gameEngine.initialiseTestGame();
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	@Test
	public void testRunOneVsOne() {
		// Initialise testing environment.
		GameEngine gameEngine = new GameEngine();
		// Prepare dummy players.
		Player[] testPlayers = new Player[2];
		BrainEngine brainEngine = null;
		try {
			brainEngine = new BrainEngine();
			brainEngine.setPath("test//snakebrain.brain");
		} catch (InvalidInstruction e) {
			fail(e.toString());
		} catch (InvalidInstructionsSet e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
		testPlayers[0] = new Player("Kieran", brainEngine.getBrainInstructions());
		testPlayers[1] = new Player("Kerry", brainEngine.getBrainInstructions());
		// Process this dummy match.
		try {
			gameEngine.runOneVsOne(testPlayers, null);
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	@Test
	public void testRunTournament() {
		// Initialise testing environment.
		GameEngine gameEngine = new GameEngine();
		// Prepare dummy players.
		Player[] testPlayers = new Player[30];
		BrainEngine brainEngine = null;
		try {
			brainEngine = new BrainEngine();
			brainEngine.setPath("test//snakebrain.brain");
		} catch (InvalidInstruction e) {
			fail(e.toString());
		} catch (InvalidInstructionsSet e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
		for (int p=0; p<30; p++) {
			testPlayers[p] = new Player("Player_" + p, brainEngine.getBrainInstructions());
		}
		// Process this dummy tournmanet.
		try {
			gameEngine.runOneVsOne(testPlayers, null);
		} catch (Exception e) {
			fail(e.toString());
		}
	}

	@Test
	public void testIncClaimedFood() {
		// Initialise testing environment.
		GameEngine gameEngine = new GameEngine();
		try {
			gameEngine.initialiseTestGame();
		} catch (Exception e) {
			fail(e.toString());
		}
		// Claimed food should initially be zero.
		assertEquals("ClaimedRedFood initialised incorrectly", 0, gameEngine.claimedRedFood);
		assertEquals("ClaimedBlackFood initialised incorrectly", 0, gameEngine.claimedBlackFood);
		// Increment both red food and black food.
		gameEngine.incClaimedFood(TeamColor.RED, 3);
		gameEngine.incClaimedFood(TeamColor.BLACK, 6);
		// Test.
		assertEquals("ClaimedRedFood incremented incorrectly", 3, gameEngine.claimedRedFood);
		assertEquals("ClaimedBlackFood incremented incorrectly", 6, gameEngine.claimedBlackFood);
	}

	@Test
	public void testGetWorld() {
		// Initialise testing environment.
		GameEngine gameEngine = new GameEngine();
		try {
			gameEngine.initialiseTestGame();
		} catch (Exception e) {
			fail(e.toString());
		}
		// Test that a world object was returned.
		assertNotNull("World not returned", gameEngine.getWorld());
	}

	@Test
	public void testGetPlayer() {
		// Initialise testing environment.
		GameEngine gameEngine = new GameEngine();
		try {
			gameEngine.initialiseTestGame();
		} catch (Exception e) {
			fail(e.toString());
		}
		// Test that a player object is returned.
		assertNotNull("Red player not returned", gameEngine.getPlayer(TeamColor.RED));
		assertNotNull("Black player not returned", gameEngine.getPlayer(TeamColor.BLACK));
	}

	@Test
	public void testRandomInt() {
		// Initialise testing environment.
		GameEngine gameEngine = new GameEngine();
		try {
			gameEngine.initialiseTestGame();
		} catch (Exception e) {
			fail(e.toString());
		}
		// Test that the returned integer values are according to the customer's specifications.
		assertEquals("Incorrent random integer returned", 7193, gameEngine.randomInt(16383));
		assertEquals("Incorrent random integer returned", 2932, gameEngine.randomInt(16383));
		assertEquals("Incorrent random integer returned", 10386, gameEngine.randomInt(16383));
		assertEquals("Incorrent random integer returned", 5575, gameEngine.randomInt(16383));
		assertEquals("Incorrent random integer returned", 100, gameEngine.randomInt(16383));
	}

}
