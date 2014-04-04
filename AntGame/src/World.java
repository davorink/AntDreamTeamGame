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
	public boolean some_ant_is_at(Pos p){
		return cells[p.getX()][p.getY()].getAnt() == null;
	}
	
	/**
	 * Returns the Ant from position p, null if no Ant.
	 * @param p Position in the World
	 * @return Ant if there is an ant in the cell at position p, null otherwise
	 */
	public Ant ant_at(Pos p){
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
	public void set_ant_at(Pos p, Ant a){
		cells[p.getX()][p.getY()].setAnt(a);
	}
	
	/**
	 * Record the fact that no ant is at position p
	 * @param p Position in the World
	 */
	public void clear_ant_at(Pos p){
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
	public Pos find_ant(int id){
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
	public void kill_ant_at(Pos p){
		clear_ant_at(p);
	}
	
	/**
	 * Get the amount of food in the cell at position p
	 * @param p Position of the cell
	 * @return amount of food in the cell at position p
	 */
	public int food_at(Pos p){
		return cells[p.getX()][p.getY()].getFoodAmount();
	}
	
	/**
	 * Set the amount of food in the cell at position p
	 * @param p Position of the cell
	 * @param f The amount of food to set at the cell position
	 */
	public void setFoodAt(Pos p, int f){
		int currentFood = food_at(p);
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
	public boolean anthill_at(Pos p, TeamColor c){
		//TODO: TeamColor must be team's color from Enums! and check if null works
		return cells[p.getX()][p.getY()].getAnthillColor() == c;
	}
	
	
	public void set_marker_at(Pos p, TeamColor c, int marker){
		cells[p.getX()][p.getY()].setMarker(c,marker,true);
	}
	
	public void clear_marker_at(Pos p, TeamColor c, int marker){
		cells[p.getX()][p.getY()].setMarker(c,marker,false);
	}
	
	public boolean check_marker_at(Pos p, TeamColor c, int marker){
		cells[p.getX()][p.getY()].getMarker(c,marker);
	}
	
	public boolean check_any_marker_at(Pos p, TeamColor c){
		for(int i=0;i<=5;i++){
			if(cells[p.getX()][p.getY()].getMarker(c,i))return true;
		}
		return false;
	}
	
	
	
	
	
	
	public boolean cell_matches(Pos p, String cond, TeamColor c){
		
		if(rocky(p)){
			if(cond.equals("Rock")){
				return true;
			}else{
				return false;
			}
		}
		else{
			if(cond.equals("Friend")){
				return some_ant_is_at(p) && ant_at(p).getColor() == c;
			}else if(cond.equals("Foe")){
				return some_ant_is_at(p) && ant_at(p).getColor() != c;
			}else if(cond.equals("FriendWithFood")){
				return some_ant_is_at(p) && ant_at(p).getColor() == c && ant_at(p).getHasFood();
			}else if(cond.equals("FoeWithFood")){
				return some_ant_is_at(p) && ant_at(p).getColor() != c && ant_at(p).getHasFood();
			}else if(cond.equals("Food")){
				return food_at(p)>0;
			}else if(cond.equals("Rock")){
				return false;
			}else if(cond.substring(0, 7).equals("Marker(") && cond.endsWith(")")){
				if(cond.charAt(7) >= '0' && cond.charAt(7) <= '5' ){
					return check_marker_at(p, c, cond.charAt(7)-48);
				}else{
					throw new Exception("Error with Marker in cell_matches!");
				}
			}else if(cond.equals("FoeMarker")){
				return check_any_marker_at(p, TeamColor.other_color(c));
			}else if(cond.equals("Home")){
				return anthill_at(p, c);
			}else if(cond.equals("FoeHome")){
				return anthill_at(p, TeamColor.other_color(c));
			}
		}
		//Something went wrong.
		throw new Exception("Something is wrong in cell_matches!");
	}
		
	
	public int adjacent_ants(Pos p, TeamColor c){
		int n = 0;
		for(int d = 0; d<=5; d++){
			Pos cel = adjacent_cell(p,d)
			if(some_ant_is_at(cel) && ant_at(cel).getColor() == c)n++;
		}
		return n;
	}
	
	
	public void check_for_surrounded_ant_at(Pos p){
		if(some_ant_is_at(p)){
			Ant a = ant_at(p);
			if(adjacent_ants(p, TeamColor.otherColor(c)) >= 5){
				kill_ant_at(p);
				set_food_at(p, food_at(p)+3+ (int)a.getHasFood());
			}
		}
	}
	
	public void check_for_surrounded_ants(Pos p){
		check_for_surrounded_ant_at(p);
		for(int d = 0; d<=5; d++){
			check_for_surrounded_ant_at(adjacent_cell(p,d));
		}
	}
	

	public Pos adjacent_cell(Pos p, int direction){
		int x = p.getX();
		int y = p.getY();
		switch(d){
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
	
	/*
	function adjacent_cell(p:pos, d:dir):pos =
			  let (x,y) = p in
			  switch d of
			    case 0: (x+1, y)
			    case 1: if evenyes then (x, y+1) else (x+1, y+1)
			    case 2: if evenyes then (x-1, y+1) else (x, y+1)
			    case 3: (x-1, y)
			    case 4: if evenyes then (x-1, y-1) else (x, y-1)
			    case 5: if evenyes then (x, y-1) else (x+1, y-1)
			    */
	
	public Cell[][] getCells(){
		return cells;
	}
	
	public Ant[] getAnts(){
		return ants;
	}
}
