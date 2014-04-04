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
	public boolean rocky(Pos p){
		return cells[p.getX()][p.getY()].getState() == CellType.ROCKY;
	}
	
	/**
	 * Checks if the Cell contains an Ant.
	 * @param p Position in the World
	 * @return true if there is an ant in the cell at position p
	 */
	public boolean someAntIsAt(Pos p){
		return cells[p.getX()][p.getY()].getAnt() == null;
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
	 */
	public Pos findAnt(int id){
		//TODO: Check if works!!!
		for(int x=0; x!=cells.length; x++){
			for(int y=0; y!=cells[x].length; y++){
				if(cells[x][y].getAnt() != null){
					if(cells[x][y].getAnt().getID == id){
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
			GameEngine.incClaimedFood(anthillColorAt(p), f); //Increment the food for the ant hill teams colour by that amount
		}	
		//TODO: Should it be enforced that the amount of food is not negative?
	}

	/**
	 * Checks if the Cell contains an Ant.
	 * @param p Position in the World
	 * @param c Colour of the anthill
	 * @return true if the cell at position p is in the anthill of color c
	 */
	public boolean anthillAt(Pos p, TeamColor c){
		//TODO: TeamColor must be team's color from Enums! and check if null works
		return cells[p.getX()][p.getY()].getAnthillColor() == c;
	}
	
	
	public void setMarkerAt(Pos p, TeamColor c, int marker){
		cells[p.getX()][p.getY()].setMarker(c,marker,true);
	}
	
	public void clearMarkerAt(Pos p, TeamColor c, int marker){
		cells[p.getX()][p.getY()].setMarker(c,marker,false);
	}
	
	public boolean checkMarkerAt(Pos p, TeamColor c, int marker){
		cells[p.getX()][p.getY()].getMarker(c,marker);
	}
	
	public boolean checkAnyMarkerAt(Pos p, TeamColor c){
		for(int i=0;i<=5;i++){
			if(cells[p.getX()][p.getY()].getMarker(c,i))return true;
		}
		return false;
	}
	
	public boolean cellMatches(Pos p, String cond, TeamColor c){
		
		if(rocky(p)){
			if(cond.equals("Rock")){
				return true;
			}else{
				return false;
			}
		}
		else{
			if(cond.equals("Friend")){
				return someAntIsAt(p) && antAt(p).getColor() == c;
			}else if(cond.equals("Foe")){
				return someAntIsAt(p) && antAt(p).getColor() != c;
			}else if(cond.equals("FriendWithFood")){
				return someAntIsAt(p) && antAt(p).getColor() == c && antAt(p).getHasFood();
			}else if(cond.equals("FoeWithFood")){
				return someAntIsAt(p) && antAt(p).getColor() != c && antAt(p).getHasFood();
			}else if(cond.equals("Food")){
				return foodAt(p)>0;
			}else if(cond.equals("Rock")){
				return false;
			}else if(cond.substring(0, 7).equals("Marker(") && cond.endsWith(")")){
				if(cond.charAt(7) >= '0' && cond.charAt(7) <= '5' ){
					return checkMarkerAt(p, c, cond.charAt(7)-48);
				}else{
					throw new Exception("Error with Marker in cell_matches!");
				}
			}else if(cond.equals("FoeMarker")){
				return checkAnyMarkerAt(p, TeamColor.otherColor(c));
			}else if(cond.equals("Home")){
				return anthillAt(p, c);
			}else if(cond.equals("FoeHome")){
				return anthillAt(p, TeamColor.otherColor(c));
			}
		}
		//Something went wrong.
		throw new Exception("Something is wrong in cell_matches!");
	}
		
	
	public int adjacentAnts(Pos p, TeamColor c){
		int n = 0;
		for(int d = 0; d<=5; d++){
			Pos cel = adjacentCell(p,d);
			if(someAntIsAt(cel) && antAt(cel).getColor() == c)n++;
		}
		return n;
	}
	
	
	public void checkForSurroundedAntAt(Pos p){
		if(someAntIsAt(p)){
			Ant a = antAt(p);
			if(adjacentAnts(p, TeamColor.otherColor(a.getColor())) >= 5){
				killAntAt(p);
				setFoodAt(p, foodAt(p)+3+(a.getHasFood()?1:0));
			}
		}
	}
	
	public void checkForSurroundedAnts(Pos p){
		checkForSurroundedAntAt(p);
		for(int d = 0; d<=5; d++){
			checkForSurroundedAntAt(adjacentCell(p,d));
		}
	}
	

	public Pos adjacentCell(Pos p, int direction){
		int x = p.getX();
		int y = p.getY();
		switch(direction){
		case 0:
			x++;
			break;
		case 1:
			if(y%2==0){
				y++;
			}else{
				x++;
				y++;
			}
			break;
		case 2:
			if(y%2==0){
				x--;
				y++;
			}else{
				y++;
			}
			break;
		case 3:
			x--;
			break;
		case 4:
			if(y%2==0){
				x--;
				y--;
			}else{
				y--;
			}
			break;
		case 5:
			if(y%2==0){
				y--;
			}else{
				x++;
				y-- ;
			}
			break;
		default:
			throw new Exception("Error in adjacent_cell!");
		}
		return new Pos(x,y);
	}
	
	public TeamColor anthillColorAt(Pos p){
		return cells[p.getX()][p.getY()].getAnthillColor();
	}
	
	
	public Cell[][] getCells(){
		return cells;
	}
	
	public Ant[] getAnts(){
		return ants;
	}
}
