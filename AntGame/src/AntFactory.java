import java.util.ArrayList;

/*
 * A class to represent an ant factory
 * @author K Hutchings
 * @version 26/03/14
 */
public class AntFactory {
	ArrayList<Ant> ants;
	
	/*
	 * Create a team of ants object with unique id's
	 * @param amount The amount of ants to create
	 * @param brainID The id of the brain the ant team it to use
	 * @param colour The colour the ant team is
	 */
	public AntFactory(int amount, int brainID, Ant.Colour colour, int direction) {
		ants = new ArrayList<Ant>();
		int id = 0;
		for (int i=0; i<amount; i++) {
			ants.add(new Ant(id, brainID, colour, direction));
			id++;
		}
	}
	
	/*
	 * Return an arrayList of the team of ants
	 * @return An array list containing the ants in the team
	 */
	public ArrayList<Ant> getAnts() {
		return ants;
	}
	
}
