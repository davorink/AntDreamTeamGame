/**
 * A class to represent a cell
 * @author K Ratusznik
 * @version 27/03/2014
 */
public class Cell {
	private CellType state;
	private Ant ant;
	private int foodAmount;
	private boolean redMarkers;
	private boolean blackMarkers;

	/**
	 * Construct an object
	 * @param state State of a cell
	 * @param ant 
	 * @param foodAmount
	 * @param redMarkers
	 * @param blackMarkers
	 */
	public Cell(CellType state, Ant ant, int foodAmount, boolean redMarkers, boolean blackMarkers) {
		this.state = state;
		this.ant = ant;
		this.foodAmount = foodAmount;
		this.redMarkers = redMarkers;
		this.blackMarkers = blackMarkers;
	}
	
	// Setters and getters
	public Ant getAnt() {
		return ant;
	}

	public void setAnt(Ant ant) {
		this.ant = ant;
	}

	public int getFoodAmount() {
		return foodAmount;
	}

	public void setFoodAmount(int foodAmount) {
		this.foodAmount = foodAmount;
	}

	public boolean isRedMarkers() {
		return redMarkers;
	}

	public void setRedMarkers(boolean redMarkers) {
		this.redMarkers = redMarkers;
	}

	public boolean isBlackMarkers() {
		return blackMarkers;
	}

	public void setBlackMarkers(boolean blackMarkers) {
		this.blackMarkers = blackMarkers;
	}

	public CellType getState() {
		return state;
	}
}
