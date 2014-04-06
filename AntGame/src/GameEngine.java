import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A class that manages games between players.
 * @author K King
 */
public class GameEngine {
	
	private static World matchWorld;
	private static Player[] matchPlayers;
	private static int claimedRedFood;
	private static int claimedBlackFood;
	private static WorldEngine worldEngine;
	// TODO: Other classes can reference these constants from here.
	public static final int STEP_INTERVAL = 10;
	public static final int FRAME_RATE = 30;
	public static final int WORLD_SIZE = 150;
	
	public GameEngine () {
		worldEngine = new WorldEngine();
	}
	
	/**
	 * This method processes a single match between two players.
	 * @param The two players for this match
	 * @param The filename of the world to play this match on, the world will be randomly generated if this parameter is null
	 */
	public static void runOneVsOne(Player[] players, String worldFile) throws Exception {
		// Define the players for this match.
		matchPlayers = new Player[2];
		matchPlayers[0] = players[0];
		matchPlayers[1] = players[1];
		// Define the player colours for this match.
		matchPlayers[0].setColor(TeamColor.RED);
		matchPlayers[1].setColor(TeamColor.BLACK);
		// Randomly generate a world if one was not provided.
		if (worldFile == null) worldFile = worldEngine.generateNewWorldFile();
		// Load the world.
		matchWorld = worldEngine.loadWorld(worldFile);
		// Play the match.
		processMatch();
		// Determine the winning player.
		if (claimedFoodParticles(matchPlayers[0].getColor()) > claimedFoodParticles(matchPlayers[1].getColor())) {
			// The first player wins.
			// JOE~ GUI.endGame(matchPlayers[0]);
		} else if (claimedFoodParticles(matchPlayers[1].getColor()) > claimedFoodParticles(matchPlayers[0].getColor())) {
			// The second player wins.
			// JOE~ GUI.endGame(matchPlayers[1]);
		} else {
			// Both players draw.
			// JOE~ GUI.endGame(null);
		}
	}
	
	/**
	 * This method processes a tournament between any number of players.
	 * @param The players to be included in the tournament
	 */
	public static void runTournament(Player[] players) throws Exception {
		
		// Generate three worlds for this tournament.
		String[] tournamentWorlds = new String[3];
		for (int index=0; index<3; index++) {
			tournamentWorlds[index] = worldEngine.generateNewWorldFile();
		}
		
		// Match each player against every other player.
		for (int A=0; A<players.length-1; A++) {
			for (int B=A+1; B<players.length; B++) {
				// Define the pairing of players for this match.
				matchPlayers = new Player[2];
				matchPlayers[0] = players[A];
				matchPlayers[1] = players[B];
				// Play this pairing of players on each of the tournament worlds.
				for (int worldIndex=0; worldIndex<tournamentWorlds.length; worldIndex++) {
					// Load the current tournament world.
					matchWorld = worldEngine.loadWorld(tournamentWorlds[worldIndex]);
					// Play the first match between this pairing of players.
					matchPlayers[0].setColor(TeamColor.RED);
					matchPlayers[1].setColor(TeamColor.BLACK);
					processMatch();
					updatePoints();
					// Reset the current tournament world.
					matchWorld = worldEngine.loadWorld(tournamentWorlds[worldIndex]);
					// Play the second match between this pairing of players.
					matchPlayers[0].setColor(TeamColor.RED);
					matchPlayers[1].setColor(TeamColor.BLACK);
					processMatch();
					updatePoints();
				}
			}
		}
		
		// Determine the winning player, this considers multiple players with the same points.
		ArrayList<Player> winningPlayers = new ArrayList<Player>();
		winningPlayers.add(players[0]);
		for (Player player : matchPlayers) {
			if (player.getPoints() > winningPlayers.get(0).getPoints()) {
				// We have found a player with the highest points, reset the ArrayList and add this player.
				winningPlayers = new ArrayList<Player>();
				winningPlayers.add(player);
			} else if (player.getPoints() == winningPlayers.get(0).getPoints()) {
				// We have found a player with the same points as the winning player, add this player.
				winningPlayers.add(player);
			}
		}
		
		// Determine if we have more than one winning player.
		if (winningPlayers.size() > 1) {
			// We have more than one winning player, run another tournament (recursively) between the winning players, this will continue until one winner remains.
			runTournament(winningPlayers.toArray(new Player[winningPlayers.size()]));
		} else {
			// We have only one winning player, the game has finished.
			// JOE~ GUI.endGame(winningPlayer.get(0));	
		}
	}	
	
	/**
	 * This method determines the winner of the current match and updates the points accordingly.
	 */
	private static void updatePoints() {
		if (claimedFoodParticles(matchPlayers[0].getColor()) > claimedFoodParticles(matchPlayers[1].getColor())) {
			// The first player wins.
			matchPlayers[0].addPoints(2);
		} else if (claimedFoodParticles(matchPlayers[1].getColor()) > claimedFoodParticles(matchPlayers[0].getColor())) {
			// The second player wins.
			matchPlayers[1].addPoints(2);
		} else {
			// Both players draw.
			matchPlayers[0].addPoints(1);
			matchPlayers[1].addPoints(1);
		}
	}
	
	/**
	 * Incrementally process each step for a match.
	 */
	private static void processMatch() {
		// Execute 300,000 steps.
		for (int stepCount=0; stepCount<300000; stepCount++) {
			// Loop through each ant according to ID.
			for (Ant ant : matchWorld.getAnts()) {
				// Process the action for this ant.
				ant.doAction();
			}
			// Check for surrounded ants.
			for (int x = 0; x < WORLD_SIZE; x++) {
				for (int y = 0; y < WORLD_SIZE; y++) {
					matchWorld.checkForSurroundedAnts(new Pos(x, y));
				}
			}
			// Return a winner if either player has no remaining ants.
			if (countAnts(TeamColor.RED) == 0) {
				claimedRedFood = 0;
				claimedBlackFood = 999;
				return;
			}
			if (countAnts(TeamColor.BLACK) == 0) {
				claimedRedFood = 999;
				claimedBlackFood = 0;
				return;
			}
			// Update the GUI.
			// TODO: We could just include parameter of the world, and transfer these methods to world so that they can be called from the GUI.
			// JOE~ GUI.stepUpdate(matchWorld.getCells(), matchWorld.getAnts(), stepCount, countAnts(TeamColor.RED), countAnts(TeamColor.BLACK), claimedFoodParticles(TeamColor.RED), claimedFoodParticles(TeamColor.BLACK));
			// Increment the step counter.
			stepCount++;
			// Sleep before executing the next step.
			try {
				Thread.sleep(STEP_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Return the number of food particles claimed by a specified player.
	 * @return Number of food particles
	 */
	private static int claimedFoodParticles(TeamColor color) {
		if (color == TeamColor.RED) return claimedRedFood;
		return claimedBlackFood;
	}
	
	/**
	 * This method increments the food claimed by a particular player.
	 * @param The colour of the team that has claimed food
	 * @param The amount of claimed food
	 */
	public static void incClaimedFood(TeamColor color, int amount) {
		if (color == TeamColor.RED) claimedRedFood += amount;
		if (color == TeamColor.BLACK) claimedBlackFood += amount;
	}
	
	/**
	 * Return the number of remaining ants of a specified player.
	 * @return Number of ants
	 */
	private static int countAnts(TeamColor color) {
		int count = 0;
		for (Ant ant : matchWorld.getAnts()) {
			if (ant.getColor() == color) count++;
		}
		return count;
	}
	
	/**
	 * Return the world for the current game.
	 * @return The world
	 */
	public static World getWorld() {
		return matchWorld;
	}
	
	/**
	 * Return the player of a specified colour in the active match.
	 * @param The colour of the player to return
	 */
	public static Player getPlayer(TeamColor color) {
		for (Player player : matchPlayers) {
			if (player.getColor() == color) {
				return player;
			}
		}
		// No player of the specified colour was found, this situation should never happen.
		return null;
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