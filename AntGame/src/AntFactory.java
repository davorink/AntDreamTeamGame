import java.util.ArrayList;

/**
 * A class to represent an ant factory
 * @author K Hutchings
 * @version 03/04/14
 */
public class AntFactory {
	ArrayList<Ant> ants;
	
	/**
	 * Create a team of ants objects
	 * @param amount The amount of ants to create
	 * @param colour The colour the ant team is
	 */
	public AntFactory(int amount, TeamColor color) {
		ants = new ArrayList<Ant>();
		int ID = 0;
		for (int i=0; i<amount; i++) {
			ants.add(new Ant(ID, color));
		}
	}
	
	/**
	 * Return an arrayList of the team of ants
	 * @return An array list containing the ants in the team
	 */
	public ArrayList<Ant> getAnts() {
		return ants;
	}
	
}
