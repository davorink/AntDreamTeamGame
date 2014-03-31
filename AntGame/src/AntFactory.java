import java.util.ArrayList;

/*
 * A class to represent an ant factory
 * @author K Hutchings
 * @version 31/03/14
 */
public class AntFactory {
	ArrayList<Ant> ants;
	
	/*
	 * Create a team of ants object with -1 as id (set in world)
	 * @param amount The amount of ants to create
	 * @param colour The colour the ant team is
	 */
	public AntFactory(int amount, String colour) {
		ants = new ArrayList<Ant>();
		int id = -1;
		for (int i=0; i<amount; i++) {
			ants.add(new Ant(colour, direction));
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
