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
	
	
	public void set_marker_at(pos p, color c, int marker){
		cells[p.getX()][p.getY()].setMarker(c,marker,true);
	}
	
	public void clear_marker_at(pos p, color c, int marker){
		cells[p.getX()][p.getY()].setMarker(c,marker,false);
	}
	
	public boolean check_marker_at(pos p, color c, int marker){
		cells[p.getX()][p.getY()].getMarker(c,marker);
	}
	
	public boolean check_any_marker_at(pos p, color c){
		for(i=0;i<=5;i++){
			if(cells[p.getX()][p.getY()].getMarker(c,i))return true;
		}
		return false;
	}
	
	
	
	
	
	
	public boolean cell_matches(pos p, String cond, color c){
		
		if(rocky(p)){
			if(cond.equals("Rock")){
				return true;
			}else{
				return false;
			}else{
				if(cond.equals("Friend")){
					return some_ant_is_at(p) && ant_at(p).getColour() == c;
				}else if(cond.equals("Foe")){
					return some_ant_is_at(p) && ant_at(p).getColour() != c;
				}else if(cond.equals("FriendWithFood")){
					return some_ant_is_at(p) && ant_at(p).getColour() == c && ant_at(p).getHasFood();
				}else if(cond.equals("FoeWithFood")){
					return some_ant_is_at(p) && ant_at(p).getColour() != c && ant_at(p).getHasFood();
				}else if(cond.equals("Food")){
					return food_at(p)>0;
				}else if(cond.equals("Rock")){
					return false;
				}else if(cond.substring(0, 7).equals("Marker(") && cond.endsWith(")")){
					if(cond.charAt(7))
				}else if(cond.equals("")){
					
				}else if(cond.equals("")){
					
				}else 
				
				
				
				
			}
		}
		

	      case Food:
	        food_at(p) > 0
	      case Rock:
	        false
	      case Marker(i):
	        check_marker_at(p, c, i)
	      case FoeMarker:
	        check_any_marker_at(p, other_color(c))
	      case Home:
	        anthill_at(p, c)
	      case FoeHome:
	        anthill_at(p, other_color(c))		
		
	}
	
	
	
	/*
	 The function cell_matches takes a position p, a condition cond, and a color c (the color of the ant that is doing the sensing), and checks whether cond holds at p.

function cell_matches(p:pos, cond:condition, c:color):bool =
  if rocky(p) then
    if cond = Rock then true else false
  else
    switch cond of
      case Friend:
        some_ant_is_at(p) &&
        color(ant_at(p)) = c
      case Foe:
        some_ant_is_at(p) &&
        color(ant_at(p)) <> c
      case FriendWithFood:
        some_ant_is_at(p) &&
        color(ant_at(p)) = c &&
        has_food(ant_at(p))
      case FoeWithFood:
        some_ant_is_at(p) &&
        color(ant_at(p)) <> c &&
        has_food(ant_at(p))
      case Food:
        food_at(p) > 0
      case Rock:
        false
      case Marker(i):
        check_marker_at(p, c, i)
      case FoeMarker:
        check_any_marker_at(p, other_color(c))
      case Home:
        anthill_at(p, c)
      case FoeHome:
        anthill_at(p, other_color(c))
	 */
	
	
}
