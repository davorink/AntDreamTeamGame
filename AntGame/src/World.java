/**
 * @author Davorin Kopic
 * @version 28/3/2014
 */
public class World {
	private Cell[][] cells;
	private Ant[] ants;
	
	/**
	 * Constructor
	 * @param cells 2D array of Cells to construct the world
	 * @param ants Array of ants in the World
	 */
	public World(Cell[][] cells, Ant[] ants){
		this.cells = cells;
		this.ants = ants;
	}
	
	/**
	 * Checks if the Cell at position p is rocky.
	 * @param p Position in the World
	 * @return true if the cell at position p is rocky
	 */
	public boolean rocky(pos p){
		return cells[p.getX()][p.getY()].getState() == ROCKY;
	}
	
	/**
	 * Checks if the Cell contains an Ant.
	 * @param p Position in the World
	 * @return true if there is an ant in the cell at position p
	 */
	public boolean some_ant_is_at(pos p){
		return cells[p.getX()][p.getY()].getAnt() == null;
	}
	
	/**
	 * Returns the Ant from position p, null if no Ant.
	 * @param p Position in the World
	 * @return Ant if there is an ant in the cell at position p, null otherwise
	 */
	public Ant ant_at(pos p){
		if(some_ant_is_at(p)){
			return cells[p.getX()][p.getY()].getAnt();
		}
		return null;
	}
	
	
	/**
	 * Record the fact that the given ant is at position p
	 * @param p Position in the World
	 * @param a Ant to be set
	 */
	public void set_ant_at(pos p, Ant a){
		cells[p.getX()][p.getY()].setAnt(a);
	}
	
	/**
	 * Record the fact that no ant is at position p
	 * @param p Position in the World
	 */
	public void clear_ant_at(pos p){
		cells[p.getX()][p.getY()].setAnt(null);
	}
	
	
	/**
	 * Checks if the Ant with id is alive.
	 * @param id Ant's id number
	 * @return true if an ant with the given id exists somewhere in the world
	 */
	public boolean ant_is_alive(int id){
		for(Ant a : ants){
			if(a.getID() == id)return true;
		}
		return false;
	}
	
	/**
	 * Returns the position of the given Ant. If Ant does not exist, returns null.
	 * @param id Ant's id number
	 * @return current position of the ant with the given id, null if not existing
	 */
	public pos find_ant(int id){
		//TODO: Check if works!!!
		for(int x=0; x!=cells.size(); x++){
			for(int y=0; y!=cells[x].size(); y++){
				if(cells[x][y].getAnt() != null){
					if(cells[x][y].getAnt().getID == id){
						return new pos(x,y);
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Kills ant at position p.
	 * @param pos Ant's position
	 */
	public void kill_ant_at(pos p){
		clear_ant_at(p);
	}
	
	/**
	 * Get the amount of food in the cell at position p
	 * @param p Position of the cell
	 * @return amount of food in the cell at position p
	 */
	public int food_at(pos p){
		return cells[p.getX()][p.getY()].getFoodAmount();
	}
	
	
	/**
	 * Set the amount of food in the cell at position p
	 * @param p Position of the cell
	 */
	public void set_food_at(pos p, int f){
		cells[p.getX()][p.getY()].setFoodAmount(f);
		//TODO: Should it be enforced that the amount of food is not negative?
	}
	

	/**
	 * Checks if the Cell contains an Ant.
	 * @param p Position in the World
	 * @param c Colour of the anthill
	 * @return true if the cell at position p is in the anthill of color c
	 */
	public boolean anthill_at(pos p, color c){
		//TODO: color must be team's color from Enums! and check if null works
		return cells[p.getX()][p.getY()].getAnthillColor() == c;
	}
	
}
