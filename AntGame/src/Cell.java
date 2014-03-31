/**
 * A class to represent a cell
 * @author K Ratusznik
 * @version 31/03/2014
 */
public class Cell {
	private cellType state;
	private Ant ant;
	private int foodAmount;
	private boolean[] redMarkers;
	private boolean[] blackMarkers;

	/**
	 * Construct an object
	 * @param state State of a cell. Can be either CLEAR or ROCKY.
	 * @param ant Ant at cell.
	 * @param foodAmount Amount of food at a cell. Can be between 
	 */
	public Cell(cellType state, int foodAmount) {
		this.state = state;
		this.foodAmount = foodAmount;
		ant = null;
		redMarkers = new boolean[6];
		blackMarkers = new boolean[6];
	}
	
	/**
	 * Get a state of a cell.
	 * @return state of a cell: CLEAR or ROCKY
	 */
	public cellType getState() {
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
	 */
	public void setMarker(color c, int marker, boolean newValue) {
		if (c == RED) {
			redMarkers[marker] = newValue;
		} else if (c == BLACK) {
			blackMarkers[marker] = newValue;
		} else {
			throw new Exception("At least one parameter is incorrect.");
		}
	}
	
	/**
	 * Get a marker.
	 * @param c Color of a marker; RED or BLACK
	 * @param i Array index
	 */
	public boolean getMarker(color c, marker i) {
		if (c == RED) {
			return redMarkers[i];
		} else if (c == BLACK) {
			return blackMarkers[i];
		} else {
			throw new Exception("At least one parameter is incorrect.");
		}
	}
}