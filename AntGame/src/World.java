/**
 * World represents the World in the Ant game. 
 * Contains cells and ants, and methods to check relationships between them.
 * @author D Kopic
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
	public boolean rocky(Pos p){
		try {
			return cells[p.getX()][p.getY()].getState() == CellType.ROCKY;
		} catch (NullPointerException e) { return true; }
	}
	
	/**
	 * Checks if the Cell contains an Ant.
	 * @param p Position in the World
	 * @return true if there is an ant in the cell at position p
	 */
	public boolean someAntIsAt(Pos p){
		try {return cells[p.getX()][p.getY()].getAnt() != null;}catch (NullPointerException e) { return false; }
	}
	
	/**
	 * Returns the Ant from position p, null if no Ant.
	 * @param p Position in the World
	 * @return Ant if there is an ant in the cell at position p, null otherwise
	 */
	public Ant antAt(Pos p){
		if(someAntIsAt(p)){
			return cells[p.getX()][p.getY()].getAnt();
		}
		return null;
	}
	
	
	/**
	 * Record the fact that the given ant is at position p
	 * @param p Position in the World
	 * @param a Ant to be set
	 */
	public void setAntAt(Pos p, Ant a){
		cells[p.getX()][p.getY()].setAnt(a);
	}
	
	/**
	 * Record the fact that no ant is at position p
	 * @param p Position in the World
	 */
	public void clearAntAt(Pos p){
		cells[p.getX()][p.getY()].setAnt(null);
	}
	
	
	/**
	 * Checks if the Ant with id is alive.
	 * @param id Ant's id number
	 * @return true if an ant with the given id exists somewhere in the world
	 */
	public boolean antIsAlive(int id){
		for(Ant a : ants){
			if(a.getID() == id)return true;
		}
		return false;
	}
	
	/**
	 * Returns the position of the given Ant. If Ant does not exist, returns null.
	 * @param id Ant's id number
	 * @return current position of the ant with the given id, null if not existing
	 * @throws WorldException if inherited from Pos
	 */
	public Pos findAnt(int id) throws WorldException{
		for(int x=0; x!=cells.length; x++){
			for(int y=0; y!=cells[x].length; y++){
				if(cells[x][y].getAnt() != null){
					if(cells[x][y].getAnt().getID() == id){
						return new Pos(x,y);
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
	public void killAntAt(Pos p){
		clearAntAt(p);
	}
	
	/**
	 * Get the amount of food in the cell at position p
	 * @param p Position of the cell
	 * @return amount of food in the cell at position p
	 */
	public int foodAt(Pos p){
		return cells[p.getX()][p.getY()].getFoodAmount();
	}
	
	/**
	 * Set the amount of food in the cell at position p
	 * @param p Position of the cell
	 * @param f The amount of food to set at the cell position
	 */
	public void setFoodAt(Pos p, int f){
		int currentFood = foodAt(p);
		cells[p.getX()][p.getY()].setFoodAmount(f);
		if (anthillColorAt(p) != null) { //If the ant is dropping food on an ant hill
			f = f-currentFood; //Take away the food in the cell already accounted for
			AntGame.engine.incClaimedFood(anthillColorAt(p), f); //Increment the food for the ant hill teams colour by that amount
		}	
	}

	/**
	 * Checks if the Cell contains an Ant.
	 * @param p Position in the World
	 * @param c Color of the anthill
	 * @return true if the cell at position p is in the anthill of color c
	 */
	public boolean anthillAt(Pos p, TeamColor c){
		if( cells[p.getX()][p.getY()].getAnthillColor() == null){
			return false;
		}else if(cells[p.getX()][p.getY()].getAnthillColor().equals(c)){
			return true;
		}
		return false;
	}
	
	/**
	 * Sets a marker for the given color on the given position in the World.
	 * @param p Position in the World
	 * @param c TeamColor of the marker 
	 * @param marker Marker index to be set
	 * @throws CellException 
	 */
	public void setMarkerAt(Pos p, TeamColor c, int marker) throws CellException{
		cells[p.getX()][p.getY()].setMarker(c,marker,true);
	}
	
	/**
	 * Clears a marker for the given color on the given position in the World.
	 * @param p Position in the World
	 * @param c TeamColor of the marker 
	 * @param marker Marker index to be cleared
	 * @throws CellException 
	 */
	public void clearMarkerAt(Pos p, TeamColor c, int marker) throws CellException{
		cells[p.getX()][p.getY()].setMarker(c,marker,false);
	}
	
	/**
	 * Checks a marker for the given color on the given position in the World.
	 * @param p Position in the World
	 * @param c TeamColor of the marker 
	 * @param marker Marker index to be checked
	 * @return True if the marker is set.
	 * @throws CellException 
	 */
	public boolean checkMarkerAt(Pos p, TeamColor c, int marker) throws CellException{
		return cells[p.getX()][p.getY()].getMarker(c,marker);
	}
	
	/**
	 * Checks ifthere is any marker for the given color on the given position in the World.
	 * @param p Position in the World
	 * @param c TeamColor of the marker 
	 * @return True if any marker for the given color is set
	 * @throws CellException 
	 */
	public boolean checkAnyMarkerAt(Pos p, TeamColor c) throws CellException{
		for(int i=0;i<=5;i++){
			if(cells[p.getX()][p.getY()].getMarker(c,i))return true;
		}
		return false;
	}
	
	/**
	 * Takes a position, a condition, and a color of the ant that is doing the sensing), 
	 * and checks whether the condition holds at the given position.
	 * @param p Position to check.
	 * @param cond Condition
	 * @param c Color of the ant.
	 * @return True if condition holds.
	 * @throws WorldException If invalid parameters inserted.
	 */
	public boolean cellMatches(Pos p, String cond, TeamColor c) throws WorldException{		
		if(rocky(p)){
			if(cond.equalsIgnoreCase("Rock")){
				return true;
			}else{
				return false;
			}
		}
		else{
			if(cond.equalsIgnoreCase("Friend")){
				return someAntIsAt(p) && antAt(p).getColor() == c;
			}else if(cond.equalsIgnoreCase("Foe")){
				return someAntIsAt(p) && antAt(p).getColor() != c;
			}else if(cond.equalsIgnoreCase("FriendWithFood")){
				return someAntIsAt(p) && antAt(p).getColor() == c && antAt(p).getHasFood();
			}else if(cond.equalsIgnoreCase("Home")){
				return anthillAt(p, c);
			}else if(cond.equalsIgnoreCase("FoeWithFood")){
				return someAntIsAt(p) && antAt(p).getColor() != c && antAt(p).getHasFood();
			}else if(cond.equalsIgnoreCase("Food")){
				return foodAt(p)>0;
			}else if(cond.equalsIgnoreCase("Rock")){
				return false;
			}else if(cond.substring(0, 7).equalsIgnoreCase("Marker(") && cond.endsWith(")")){
				if(cond.charAt(7) >= '0' && cond.charAt(7) <= '5' ){
					return checkMarkerAt(p, c, cond.charAt(7)-48);
				}else{
					throw new WorldException("Error with Marker in cell_matches!");
				}
			}else if(cond.equalsIgnoreCase("FoeMarker")){
				return checkAnyMarkerAt(p, TeamColor.otherColor(c));
			}else if(cond.equalsIgnoreCase("FoeHome")){
				return anthillAt(p, TeamColor.otherColor(c));
			}
		}
		//Something went wrong.
		throw new WorldException("Something is wrong in cellMatches!");
	}
		
	/**
	 * Returns number of enemy ants adjacent to the given ant.
	 * @param p Position of the ant
	 * @param c Color of the ant
	 * @return Number of enemy ants adjacent
	 * @throws WorldException if WorldException inherited from adjacentCell()
	 */
	public int adjacentAnts(Pos p, TeamColor c) throws WorldException{
		int n = 0;
		for(int d = 0; d<=5; d++){
			Pos cel = adjacentCell(p,d);
			if(someAntIsAt(cel) && antAt(cel).getColor() == c)n++;
		}
		return n;
	}
	
	/**
	 * Checks the number of enemy ants adjacent to the given ant and kills it if more than 5.
	 * @param p Position of the ant
	 * @throws WorldException if WorldException inherited from adjacentAnts()
	 */
	public void checkForSurroundedAntAt(Pos p) throws WorldException{
		if(someAntIsAt(p)){
			Ant a = antAt(p);
			if(adjacentAnts(p, TeamColor.otherColor(a.getColor())) >= 5){
				killAntAt(p);
				setFoodAt(p, foodAt(p)+3+(a.getHasFood()?1:0));
			}
		}
	}
	
	/**
	 * Checks for surrounded ants.
	 * @param p Position of the ant
	 * @throws WorldException if WorldException inherited from adjacentCell()
	 */
	public void checkForSurroundedAnts(Pos p) throws WorldException{
		checkForSurroundedAntAt(p);
		for(int d = 0; d<=5; d++){
			checkForSurroundedAntAt(adjacentCell(p,d));
		}
	}
	
	/**
	 * Calculates the position of the cell adjacent to p in the given direction.
	 * @param p Position of the Cell
	 * @param direction Direction to calculate.
	 * @return Position of the adjacent cell in given direction.
	 * @throws Exception If wrong parameters inserted.
	 */
	public Pos adjacentCell(Pos p, int direction) throws WorldException{
		try {int x = p.getX();
		int y = p.getY();
		switch(direction){
		case 0:
			x++;
			break;
		case 1:
			if(y%2==0){y++;}
			else{x++;y++;}
			break;
		case 2:
			if(y%2==0){x--;y++;}
			else{y++;}
			break;
		case 3:
			x--;
			break;
		case 4:
			if(y%2==0){x--;y--;}
			else{y--;}
			break;
		case 5:
			if(y%2==0){y--;}
			else{x++;y--;}
			break;
		default:
			throw new WorldException("Error in adjacentCell!");
		} return new Pos(x,y); } catch(NullPointerException e) {
			throw new WorldException("Error in adjacentCell!"); }
	}
	
	/**
	 * Get the anthill color at the position. 
	 * @param p Position to check.
	 * @return Color of the anthill at p, null if no anthill.
	 */
	public TeamColor anthillColorAt(Pos p){
		return cells[p.getX()][p.getY()].getAnthillColor();
	}
	
	/**
	 * Get all cells in the world.
	 * @return All cells in the World.
	 */
	public Cell[][] getCells(){
		return cells;
	}
	
	/**
	 * Get all ants in the world.
	 * @return All ants in the World.
	 */
	public Ant[] getAnts(){
		return ants;
	}
}

/**
 * Class WorldException is used to report exceptions in the World
 * @author D Kopic
 */
@SuppressWarnings("serial")
class WorldException extends Exception {
	public String msg;
    
    public WorldException(String _msg){
        super(_msg);
        msg = _msg;
    }
}