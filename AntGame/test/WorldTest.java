import static org.junit.Assert.*;

import org.junit.Test;


public class WorldTest {

	private final int ANTHILLRX=81;
	private final int ANTHILLRY=34;
	private final int ANTHILLBX=120;
	private final int ANTHILLBY=135;
	private final int ROCKX=0;
	private final int ROCKY=0;
	private final int CLEARX=1;
	private final int CLEARY=1;
	private final int FOODX=31;
	private final int FOODY=41;
	
	@Test
	public void testWorld() throws Exception {
		WorldEngine we = new WorldEngine();
		World w = we.loadWorld("test\\tiny.world"); //we.loadWorld(we.generateNewWorldFile("test\\test.world"));//
		assertNotNull(w);
	}

	@Test
	public void testRocky() throws Exception {
		AntGame.engine = new GameEngine();
		AntGame.engine.initialiseTestGame();//"test\\test.world"
		World w = AntGame.engine.getWorld();
		/*
	     * Checks if the Cell at position p is rocky. 
	     * @param p Position in the World 
	     * @return true if the cell at position p is rocky 
	     */
		assertTrue(w.rocky(new Pos(ROCKY,ROCKX)));//Rock
		assertFalse(w.rocky(new Pos(CLEARY,CLEARX)));//Clear
		assertFalse(w.rocky(new Pos(ANTHILLRY,ANTHILLRX)));//Anthill -
		assertFalse(w.rocky(new Pos(ANTHILLBY,ANTHILLBX)));//Anthill +
		assertFalse(w.rocky(new Pos(FOODY,FOODX)));//Food
	}

	@Test
	public void testSomeAntIsAt() throws Exception {
		AntGame.engine = new GameEngine();
		AntGame.engine.initialiseTestGame();//"test\\test.world"
		World w = AntGame.engine.getWorld();

	    /*
	     * Checks if the Cell contains an Ant. 
	     * @param p Position in the World 
	     * @return true if there is an ant in the cell at position p 
	     */
		assertTrue(w.someAntIsAt(new Pos(ANTHILLRY,ANTHILLRX)));//Anthill -
		assertTrue(w.someAntIsAt(new Pos(ANTHILLBY,ANTHILLBX)));//Anthill +
		assertFalse(w.someAntIsAt(new Pos(ROCKY,ROCKX)));//Rock
		assertFalse(w.someAntIsAt(new Pos(CLEARY,CLEARX)));//Clear
		assertFalse(w.someAntIsAt(new Pos(FOODY,FOODX)));//Food
	}

	@Test
	public void testAntAt_Clear_Set_KillAntAt() throws Exception {
		AntGame.engine = new GameEngine();
		AntGame.engine.initialiseTestGame();//"test\\test.world"
		World w = AntGame.engine.getWorld();
		/* AntAt
	     * Returns the Ant from position p, null if no Ant. 
	     * @param p Position in the World 
	     * @return Ant if there is an ant in the cell at position p, null otherwise 
	     */
		/*Set
		 * Record the fact that the given ant is at position p
		 * @param p Position in the World
		 * @param a Ant to be set
		 */
		/*Clear
		 * Record the fact that no ant is at position p
		 * @param p Position in the World
		 */
		/*Kill
		 * Kills ant at position p.
		 * @param pos Ant's position
		 */
		assertNotNull(w.antAt(new Pos(ANTHILLRY,ANTHILLRX)));//Anthill -
		assertNotNull(w.antAt(new Pos(ANTHILLBY,ANTHILLBX)));//Anthill +
		assertNull(w.antAt(new Pos(ROCKY,ROCKX)));//Rock
		assertNull(w.antAt(new Pos(CLEARY,CLEARX)));//Clear
		assertNull(w.antAt(new Pos(FOODY,FOODX)));//Food
		w.setAntAt(new Pos(CLEARX, CLEARY), new Ant(0,TeamColor.RED));
		assertNotNull(w.antAt(new Pos(CLEARX, CLEARY)));
		w.clearAntAt(new Pos(CLEARX, CLEARY));
		assertNull(w.antAt(new Pos(CLEARY,CLEARX)));
		w.setAntAt(new Pos(CLEARX, CLEARY), new Ant(0,TeamColor.RED));
		w.killAntAt(new Pos(CLEARX, CLEARY));
		assertNull(w.antAt(new Pos(CLEARY,CLEARX)));
	}

	@Test
	public void testAntIsAlive() throws Exception {
		AntGame.engine = new GameEngine();
		AntGame.engine.initialiseTestGame();//"test\\test.world"
		World w = AntGame.engine.getWorld();
		/*
		 * Checks if the Ant with id is alive.
		 * @param id Ant's id number
		 * @return true if an ant with the given id exists somewhere in the world
		 */
		assertTrue(w.antIsAlive(5));
		assertFalse(w.antIsAlive(9999));
	}

	@Test
	public void testFindAnt() throws Exception {
		AntGame.engine = new GameEngine();
		AntGame.engine.initialiseTestGame();//"test\\test.world"
		World w = AntGame.engine.getWorld();
		/*
		 * Returns the position of the given Ant. If Ant does not exist, returns null.
		 * @param id Ant's id number
		 * @return current position of the ant with the given id, null if not existing
		 */
		System.out.println(w.findAnt(0).getX()+" "+w.findAnt(0).getY());
		assertTrue(w.findAnt(0).getX()==28);
		assertTrue(w.findAnt(0).getY()==75);
		assertNull(w.findAnt(9999));
	}


	@Test
	public void testFoodAt() throws Exception {
		AntGame.engine = new GameEngine();
		AntGame.engine.initialiseTestGame();//"test\\test.world"
		World w = AntGame.engine.getWorld();
		/* 
	     * Get the amount of food in the cell at position p
		 * @param p Position of the cell
		 * @return amount of food in the cell at position p
		 */
		assertEquals(0, w.foodAt(new Pos(ANTHILLRY,ANTHILLRX)));//Anthill -
		assertEquals(0, w.foodAt(new Pos(ANTHILLBY,ANTHILLBX)));//Anthill +
		assertEquals(0, w.foodAt(new Pos(ROCKY,ROCKX)));//Rock
		assertEquals(0, w.foodAt(new Pos(CLEARY,CLEARX)));//Clear
		assertEquals(5, w.foodAt(new Pos(FOODY,FOODX)));//Food
	}

	@Test
	public void testSetFoodAt() throws Exception {
		AntGame.engine = new GameEngine();
		AntGame.engine.initialiseTestGame();//"test\\test.world"
		World w = AntGame.engine.getWorld();
		/*
		 * Set the amount of food in the cell at position p
		 * @param p Position of the cell
		 * @param f The amount of food to set at the cell position
		 */
		w.setFoodAt(new Pos(ANTHILLRY,ANTHILLRX),1);
		w.setFoodAt(new Pos(ANTHILLBY,ANTHILLBX),1);
		w.setFoodAt(new Pos(CLEARY,CLEARX),1);
		w.setFoodAt(new Pos(FOODY,FOODX),1);
		
		assertEquals(1, w.foodAt(new Pos(ANTHILLRY,ANTHILLRX)));//Anthill -
		assertEquals(1, w.foodAt(new Pos(ANTHILLBY,ANTHILLBX)));//Anthill +
		assertEquals(1, w.foodAt(new Pos(CLEARY,CLEARX)));//Clear
		assertEquals(1, w.foodAt(new Pos(FOODY,FOODX)));//Food
	}

	
	

	@Test
	public void testAnthillAt() throws Exception {
		AntGame.engine = new GameEngine();
		AntGame.engine.initialiseTestGame();//"test\\test.world"
		World w = AntGame.engine.getWorld();
		/*
		 * Checks if the Cell contains an Anthill of color c.
		 * @param p Position in the World
		 * @param c Color of the anthill
		 * @return true if the cell at position p is in the anthill of color c
		 */
		assertTrue(w.anthillAt(new Pos(ANTHILLRY,ANTHILLRX), TeamColor.RED));//Anthill -
		assertTrue(w.anthillAt(new Pos(ANTHILLBY,ANTHILLBX), TeamColor.BLACK));//Anthill +
		assertFalse(w.anthillAt(new Pos(ANTHILLRY,ANTHILLRX), TeamColor.BLACK));//Anthill -
		assertFalse(w.anthillAt(new Pos(ANTHILLBY,ANTHILLBX), TeamColor.RED));//Anthill +
		assertFalse(w.anthillAt(new Pos(ROCKY,ROCKX), TeamColor.RED));//Rock
		assertFalse(w.anthillAt(new Pos(CLEARY,CLEARX), TeamColor.RED));//Clear
		assertFalse(w.anthillAt(new Pos(FOODY,FOODX), TeamColor.RED));//Food
	}

	@Test
	public void testSet_Check_Clear_CheckAnyMarkerAt() throws Exception {
		AntGame.engine = new GameEngine();
		AntGame.engine.initialiseTestGame();//"test\\test.world"
		World w = AntGame.engine.getWorld();
		/* Set
		 * Sets a marker for the given color on the given position in the World.
		 * @param p Position in the World
		 * @param c TeamColor of the marker 
		 * @param marker Marker index to be set
		 */
		/* Clear
		 * Clears a marker for the given color on the given position in the World.
		 * @param p Position in the World
		 * @param c TeamColor of the marker 
		 * @param marker Marker index to be cleared
		 */
		/* Check
		 * Checks a marker for the given color on the given position in the World.
		 * @param p Position in the World
		 * @param c TeamColor of the marker 
		 * @param marker Marker index to be checked
		 * @return True if the marker is set.
		 */
		/*
		 * Checks if there is any marker for the given color on the given position in the World.
		 * @param p Position in the World
		 * @param c TeamColor of the marker 
		 * @return True if any marker for the given color is set
		 */
		w.setMarkerAt(new Pos(CLEARY,CLEARX), TeamColor.RED,0);
		assertTrue(w.checkMarkerAt(new Pos(CLEARY,CLEARX), TeamColor.RED,0));
		assertTrue(w.checkAnyMarkerAt(new Pos(CLEARY,CLEARX), TeamColor.RED));
		w.clearMarkerAt(new Pos(CLEARY,CLEARX), TeamColor.RED,0);
		assertFalse(w.checkMarkerAt(new Pos(CLEARY,CLEARX), TeamColor.RED,0));
		assertFalse(w.checkAnyMarkerAt(new Pos(CLEARY,CLEARX), TeamColor.RED));
	}


	@Test
	public void testCellMatches() throws Exception {
		AntGame.engine = new GameEngine();
		AntGame.engine.initialiseTestGame();//"test\\test.world"
		World w = AntGame.engine.getWorld();
		/*
		 * Takes a position, a condition, and a color of the ant that is doing the sensing), 
		 * and checks whether the condition holds at the given position.
		 * @param p Position to check.
		 * @param cond Condition
		 * @param c Color of the ant.
		 * @return True if condition holds.
		 */
		assertTrue(w.cellMatches(new Pos(ROCKY,ROCKX), "rock", null));
		assertTrue(w.cellMatches(new Pos(FOODY, FOODX), "food", null));
		assertFalse(w.cellMatches(new Pos(CLEARY, CLEARX), "marker(0)", TeamColor.RED));
		assertFalse(w.cellMatches(new Pos(CLEARY, CLEARX), "foemarker", TeamColor.RED));
		assertFalse(w.cellMatches(new Pos(ANTHILLBY, ANTHILLBX), "foewithfood", TeamColor.RED));
		assertFalse(w.cellMatches(new Pos(ANTHILLBY, ANTHILLBX), "friendwithfood", TeamColor.RED));
		assertTrue(w.cellMatches(new Pos(ANTHILLBY, ANTHILLBX), "friend", TeamColor.BLACK));
		assertTrue(w.cellMatches(new Pos(ANTHILLBY, ANTHILLBX), "foe", TeamColor.RED));
		assertTrue(w.cellMatches(new Pos(ANTHILLBY, ANTHILLBX), "home", TeamColor.BLACK));
		assertTrue(w.cellMatches(new Pos(ANTHILLBY, ANTHILLBX), "foehome", TeamColor.RED));
	}

	@Test
	public void testAdjacentAnts() throws Exception{
		AntGame.engine = new GameEngine();
		AntGame.engine.initialiseTestGame();//"test\\test.world"
		World w = AntGame.engine.getWorld();
		/*
		 * Returns number of enemy ants adjacent to the given ant.
		 * @param p Position of the ant
		 * @param c Color of the ant
		 * @return Number of enemy ants adjacent
		 */
		w.setAntAt(new Pos(ANTHILLBY,ANTHILLBX),new Ant(0,TeamColor.RED));
		w.setAntAt(new Pos(ANTHILLBY+1,ANTHILLBX),new Ant(0,TeamColor.BLACK));
		w.setAntAt(new Pos(ANTHILLBY+1,ANTHILLBX+1),new Ant(0,TeamColor.BLACK));
		w.setAntAt(new Pos(ANTHILLBY,ANTHILLBX+1),new Ant(0,TeamColor.BLACK));
		w.setAntAt(new Pos(ANTHILLBY-1,ANTHILLBX),new Ant(0,TeamColor.BLACK));
		w.setAntAt(new Pos(ANTHILLBY,ANTHILLBX-1),new Ant(0,TeamColor.BLACK));
		w.setAntAt(new Pos(ANTHILLBY+1,ANTHILLBX-1),new Ant(0,TeamColor.BLACK));
		assertEquals(6,w.adjacentAnts(new Pos(ANTHILLBY,ANTHILLBX), TeamColor.BLACK));
		assertEquals(1,w.adjacentAnts(new Pos(ANTHILLBY+1,ANTHILLBX), TeamColor.RED));
	}

	@Test
	public void testCheckForSurroundedAntAt() throws Exception{
		AntGame.engine = new GameEngine();
		AntGame.engine.initialiseTestGame();//"test\\test.world"
		World w = AntGame.engine.getWorld();
		/*
		 * Checks the number of enemy ants adjacent to the given ant 
		 * and kills it if more than 5.
		 * @param p Position of the ant
		 */
		w.setAntAt(new Pos(ANTHILLBY,ANTHILLBX),new Ant(0,TeamColor.RED));
		w.setAntAt(new Pos(ANTHILLBY+1,ANTHILLBX),new Ant(0,TeamColor.BLACK));
		w.setAntAt(new Pos(ANTHILLBY+1,ANTHILLBX+1),new Ant(0,TeamColor.BLACK));
		w.setAntAt(new Pos(ANTHILLBY,ANTHILLBX+1),new Ant(0,TeamColor.BLACK));
		w.setAntAt(new Pos(ANTHILLBY-1,ANTHILLBX),new Ant(0,TeamColor.BLACK));
		w.setAntAt(new Pos(ANTHILLBY,ANTHILLBX-1),new Ant(0,TeamColor.BLACK));
		w.setAntAt(new Pos(ANTHILLBY+1,ANTHILLBX-1),new Ant(0,TeamColor.BLACK));
		
		assertEquals(6,w.adjacentAnts(new Pos(ANTHILLBY,ANTHILLBX), TeamColor.BLACK));
		w.checkForSurroundedAntAt(new Pos(ANTHILLBY,ANTHILLBX));
		assertEquals(0,w.adjacentAnts(new Pos(ANTHILLBY+1,ANTHILLBX), TeamColor.RED));
		

	}

	@Test
	public void testCheckForSurroundedAnts() throws Exception{
		AntGame.engine = new GameEngine();
		AntGame.engine.initialiseTestGame();//"test\\test.world"
		World w = AntGame.engine.getWorld();
		/*
		 * Checks the number of enemy ants adjacent to the given ant 
		 * and kills it if more than 5.
		 * @param p Position of the ant
		 */		
		w.setAntAt(new Pos(ANTHILLBY,ANTHILLBX),new Ant(0,TeamColor.RED));
		w.setAntAt(new Pos(ANTHILLBY+1,ANTHILLBX),new Ant(0,TeamColor.BLACK));
		w.setAntAt(new Pos(ANTHILLBY+1,ANTHILLBX+1),new Ant(0,TeamColor.BLACK));
		w.setAntAt(new Pos(ANTHILLBY,ANTHILLBX+1),new Ant(0,TeamColor.BLACK));
		w.setAntAt(new Pos(ANTHILLBY-1,ANTHILLBX),new Ant(0,TeamColor.BLACK));
		w.setAntAt(new Pos(ANTHILLBY,ANTHILLBX-1),new Ant(0,TeamColor.BLACK));
		w.setAntAt(new Pos(ANTHILLBY+1,ANTHILLBX-1),new Ant(0,TeamColor.BLACK));
		
		assertEquals(6,w.adjacentAnts(new Pos(ANTHILLBY,ANTHILLBX), TeamColor.BLACK));
		w.checkForSurroundedAnts(new Pos(ANTHILLBY,ANTHILLBX));
		assertEquals(0,w.adjacentAnts(new Pos(ANTHILLBY+1,ANTHILLBX), TeamColor.RED));
		
	}

	@Test
	public void testAdjacentCell() throws Exception {
		AntGame.engine = new GameEngine();
		AntGame.engine.initialiseTestGame();//"test\\test.world"
		World w = AntGame.engine.getWorld();
		/*
		 * Calculates the position of the cell adjacent to p in the given direction.
		 * @param p Position of the Cell
		 * @param direction Direction to calculate.
		 * @return Position of the adjacent cell in given direction.
		 */
		Pos p = new Pos(3,3);
		assertEquals(4, w.adjacentCell(p,0).getX());
		assertEquals(3, w.adjacentCell(p,0).getY());
		assertEquals(4, w.adjacentCell(p,1).getX());
		assertEquals(4, w.adjacentCell(p,1).getY());
		assertEquals(3, w.adjacentCell(p,2).getX());
		assertEquals(4, w.adjacentCell(p,2).getY());
		assertEquals(2, w.adjacentCell(p,3).getX());
		assertEquals(3, w.adjacentCell(p,3).getY());
		assertEquals(3, w.adjacentCell(p,4).getX());
		assertEquals(2, w.adjacentCell(p,4).getY());
		assertEquals(4, w.adjacentCell(p,5).getX());
		assertEquals(2, w.adjacentCell(p,5).getY());

		p = new Pos(3,2);
		assertEquals(4, w.adjacentCell(p,0).getX());
		assertEquals(2, w.adjacentCell(p,0).getY());
		assertEquals(3, w.adjacentCell(p,1).getX());
		assertEquals(3, w.adjacentCell(p,1).getY());
		assertEquals(2, w.adjacentCell(p,2).getX());
		assertEquals(3, w.adjacentCell(p,2).getY());
		assertEquals(2, w.adjacentCell(p,3).getX());
		assertEquals(2, w.adjacentCell(p,3).getY());
		assertEquals(2, w.adjacentCell(p,4).getX());
		assertEquals(1, w.adjacentCell(p,4).getY());
		assertEquals(3, w.adjacentCell(p,5).getX());
		assertEquals(1, w.adjacentCell(p,5).getY());
	}

	@Test
	public void testAnthillColorAt() throws Exception {
		AntGame.engine = new GameEngine();
		AntGame.engine.initialiseTestGame();//"test\\test.world"
		World w = AntGame.engine.getWorld();
		/*
		 * Get the anthill color at the position. 
		 * @param p Position to check.
		 * @return Color of the anthill at p, null if no anthill.
		 */
		assertEquals(TeamColor.RED, w.anthillColorAt(new Pos(ANTHILLRY,ANTHILLRX)));//Anthill -
		assertEquals(TeamColor.BLACK, w.anthillColorAt(new Pos(ANTHILLBY,ANTHILLBX)));//Anthill +
		assertEquals(null, w.anthillColorAt(new Pos(CLEARY,CLEARX)));//Not anthill
	}
}
