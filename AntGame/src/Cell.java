/**
 * A class to represent a cell
 * @author K Ratusznik
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
	 * @throws CellException When not valid food amount
	 */
	public Cell(CellType state, int foodAmount) throws CellException {
		if (state.equals(CellType.CLEAR) || state.equals(CellType.ROCKY)) {
			this.state = state;
		} else {
			//throw new Exception("Invalid cell type");
			System.out.println("Invalid cell type. Expected CLEAR or ROCKY cell type");
		}
		if (foodAmount < 0 || foodAmount > 9) {
			throw new CellException("Invalid food amount. Expected value in range 0-9 but found " + foodAmount);
		} else {
			this.foodAmount = foodAmount;
		}
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
	 * @throws CellException 
	 * @throws Exception When not valid parameters
	 */
	public void setMarker(TeamColor c, int index, boolean newValue) throws CellException { 
		if (c == TeamColor.RED) {
			redMarkers[index] = newValue;
		} else if (c == TeamColor.BLACK) {
			blackMarkers[index] = newValue;
		} else {
			throw new CellException("At least one parameter is incorrect.");
		}
	}
	
	/**
	 * Get a marker.
	 * @param c Color of a marker; RED or BLACK
	 * @param i Array index
	 * @throws CellException When nonexisting color of the marker 
	 */
	public boolean getMarker(TeamColor c, int index) throws CellException { 
		if (c == TeamColor.RED) {
			return redMarkers[index];
		}
		else if (c == TeamColor.BLACK) {
			return blackMarkers[index];
		} else {
			throw new CellException("Non-existing color.");
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

/**
 * Class CellException is used to report exceptions in the Cell
 * @author D Kopic
 */
@SuppressWarnings("serial")
class CellException extends WorldException {
	public String msg;
    
    public CellException(String _msg){
        super(_msg);
        msg = _msg;
    }
}