/**
 * A class to represent a cell
 * @author K Ratusznik
 * @version 01/04/2014
 */
public class Cell {
	private CellType state;
	private Ant ant;
	private int foodAmount;
	private boolean[] redMarkers;
	private boolean[] blackMarkers;
	private TeamColor anthill;

	/**
	 * Construct an object
	 * @param state State of a cell. Can be either CLEAR or ROCKY.
	 * @param ant Ant at cell.
	 * @param foodAmount Amount of food at a cell. Can be between 
	 */
	public Cell(CellType state, int foodAmount) {
		this.state = state;
		this.foodAmount = foodAmount;
		ant = null;
		redMarkers = new boolean[6];
		blackMarkers = new boolean[6];
		anthill = null;
	}
	
	/**
	 * Get a state of a cell.
	 * @return state of a cell: CLEAR or ROCKY
	 */
	public CellType getState() {
		return state;
	}
	
	/**
	 * Get an ant.
	 * @return ant
	 */
	public Ant getAnt() {
		return ant;
	}

	/**
	 * Set ant.
	 * @param ant  
	 */
	public void setAnt(Ant ant) {
		this.ant = ant;
	}

	/**
	 * Get a number of food particles.
	 * @return a number of food particles
	 */
	public int getFoodAmount() {
		return foodAmount;
	}

	/**
	 * Set amount of food in a cell.
	 * @param foodAmount 
	 */
	public void setFoodAmount(int foodAmount) {
		this.foodAmount = foodAmount;
	}
	
	/**
	 * Set a marker.
	 * @param c Color of a marker; RED or BLACK
	 * @param marker Array index
	 * @param newValue New value of a marker
	 * @throws Exception 
	 */
	public void setMarker(TeamColor c, int index, boolean newValue) throws Exception {
		if (c == TeamColor.RED) {
			redMarkers[index] = newValue;
		} else if (c == TeamColor.BLACK) {
			blackMarkers[index] = newValue;
		} else {
			throw new Exception("At least one parameter is incorrect.");
		}
	}
	
	/**
	 * Get a marker.
	 * @param c Color of a marker; RED or BLACK
	 * @param i Array index
	 * @throws Exception 
	 */
	public boolean getMarker(TeamColor c, int index) throws Exception {
		if (c == TeamColor.RED) {
			return redMarkers[index];
		} else if (c == TeamColor.BLACK) {
			return blackMarkers[index];
		} else {
			throw new Exception("At least one parameter is incorrect.");
		}
	}
	
	/**
	 * Get anthill TeamColor.
	 * @return anthill TeamColor; RED or BLACK
	 */
	public TeamColor getAnthillColor() {
		return anthill;
	}
	
	/**
	 * Set anthill TeamColor. RED or BLACK
	 * @param c Color of an anthill
	 */
	public void setAnthillColor(TeamColor c) {
		this.anthill = c;
	}
}