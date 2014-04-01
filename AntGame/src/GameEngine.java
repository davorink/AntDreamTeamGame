import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A class that manages games between players.
 * @author K King
 * @version 01/04/2014
 */
public class GameEngine {
	
	private WorldEngine worldEngine;
	private int round;
	private int roundInterval;
	// TODO: Other classes can reference these constants from here.
	public final int FRAMERATE = 30;
	public final int WORLDSIZE = 150;
	
	public GameEngine () {
		worldEngine = new WorldEngine();
		round = 0;
		// Time to sleep between rounds in milliseconds.
		this.roundInterval = 10;
	}
	

	public void runMatch(Player[] players, String[] worlds) {
		// Player handling.
		if (players.length == 2) {
			// Set player colors.
			players[0].setColor(Color.RED);
			players[1].setColor(Color.BLACK);
		} else {
			// TODO: Parameter error, two players must be supplied.
		}
		// World handling.
		World[] matchWorlds = new World[3];
		if (worlds == null) {
			// Generate three worlds because none were supplied.
			for (int worldNumber=0; worldNumber<3; worldNumber++) {
				matchWorlds[worldNumber] = worldEngine.generateWorld();
			}
		} else if (worlds.length == 3) {
			// Load the three supplied world files.
			for (int worldNumber=0; worldNumber<3; worldNumber++) {
				// TODO: These need to validated when selected in GUI.
				matchWorlds[worldNumber] = worldEngine.loadWorld(worlds[worldNumber]);
			}
		} else {
			// TODO: Parameter error, three worlds must be supplied or be null.
		}
		// Play up to three games between the two supplied players.
		int redWinCount = 0;
		int blackWinCount = 0;
		Color gameWinner;
		gameloop:
		for (int gameNumber=0; gameNumber<3; gameNumber++) {
			// Run this game.
			gameWinner = processRounds();
			// Increase the win count for the winner of this game round.
			playerloop:
			for (Player player : players) {
				if (player.getColor() == gameWinner) {
					player.incrementWinCount();
					if (player.getWinCount() >= 2) {
						// This player wins if two or more games have been won.
						GUI.endMatch(player);
						// End this class.
						break gameloop;
					} else {
						// Display the winner for this game of the match.
						GUI.displayGameWinner(player);
					}
					// We don't need to continue this loop, there can only be one winner per game round.
					break playerloop;
				}
			}
			// TODO: Swap player colors.
			// Reset game round.
			// Change world.
		}
	}
	
	/**
	 * Incrementally process each round for the current game.
	 * @return The color of the winning team
	 */
	public Color processRounds() {
		// Execute 300,000 rounds.
		while (round < 300000) {
			// Loop through each ant according to ID.
			for (Ant ant : world.ants) {
				// Process the action for this ant.
				ant.doAction();
			}
			// Check for surrounded ants.
			for (int x = 0; x < WORLDSIZE; x++) {
				for (int y = 0; y < WORLDSIZE; y++) {
					world.check_for_surrounded_ants(new pos(x, y));
				}
			}
			// Return a winner if either player has no remaining ants.
			if (countAnts(Color.RED) == 0) return Color.RED;
			if (countAnts(Color.BLACK) == 0) return Color.BLACK;
			// Update the GUI.
			// TODO: We could just include parameter of the world, and transfer these methods to world so that they can be called from the GUI.
			GUI.roundUpdate(world.getCells(), world.getAnts(), round, countAnts(Color.RED), countAnts(Color.BLACK), countFoodParticles(Color.RED), countFoodParticles(Color.BLACK));
			// Increment the round counter.
			round++;
			// Sleep before executing the next round.
			try {
				Thread.sleep(roundInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// The game has ended, return the winning player.
		if (countFoodParticles(Color.RED) > countFoodParticles(Color.BLACK)) {
			return Color.RED;
		} else {
			return Color.BLACK;
		}
	}
	
	/**
	 * Return the number of food particles claimed by a specified player.
	 * @return Number of food particles
	 */
	private int countFoodParticles(Color color) {
		// TODO: Cell must contain anthill state as ENUM color or null.
		return 0;
	}
	
	/**
	 * Return the number of remaining ants of a specified player.
	 * @return Number of ants
	 */
	private int countAnts(Color color) {
		int count = 0;
		for (Ant ant : world.getAnts()) {
			if (ant.getColor() == color) count++;
		}
		return count;
	}
	
	/**
	 * Return the world for the current game.
	 * @return The world
	 */
	public World getWorld() {
		return world;
	}
	
	/**
	 * Return a random number from 0 to n-1.
	 * @param The upper boundary of the random number, such that the returned number is between 0 and n-1
	 * @return Random number
	 */
	public static int randomInt(int n) {
		// TODO!
		return 0;
	}
	
}
