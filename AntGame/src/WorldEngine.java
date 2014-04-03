/**
 * A class to represent world engine
 * @author K Ratusznik and D Kopic
 * @version 03/04/2014
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class WorldEngine {
	private BufferedReader bfr;
	private int x;
	private int y;
	private Cell[][] grid;
	private static String file;
	private final int WORLDSIZE = 150;
	private World world;
	
	/**
	 * Default constructor.
	 * @throws IOException 
	 */
	public WorldEngine(String fname) throws IOException{	
		world = loadWorld(fname);
		printWorld(world);
	}
	
	/**
	 * Load world from a specified file. 
	 * @param fname Name of file to load the world from
	 * @throws IOException
	 */
	public World loadWorld(String fname) throws IOException {
		FileReader fr = new FileReader(fname);
		bfr = new BufferedReader(fr);
		String line;
		char curChar;
		ArrayList<Ant> ants = new ArrayList<Ant>(); 
		int rockCount = 14;
		
		try { // Check if x and y values are correct
			x = Integer.parseInt(bfr.readLine());
			if (x != WORLDSIZE) throw new Exception("World's x dimention is not valid.");
			y = Integer.parseInt(bfr.readLine());
			if (y != WORLDSIZE) throw new Exception("World's y dimention is not valid.");
		} catch (Exception e) {
			System.err.println("Caught Exception: Error when parsing x and y variables.");
		}
		
		grid = new Cell[x][y];
		
		try {
			int antCount = 0;	
			int row = 0;
			/* While there is a line to read */
			while((line = bfr.readLine()) != null) {
				String[] curLineStr = line.split(" ");
				/* change String array to char array */
				char[] curLine = new char[curLineStr.length];
				for(int j = 0; j < curLineStr.length; j++) {
					curLine[j] = curLineStr[j].charAt(0);
				}
				
				if (curLine.length != WORLDSIZE)  throw new Exception("Invalid column size at row " + row);
				/* For each character create appropriate cell */
				for(int col = 0; col < curLine.length; col++) {
					curChar = curLine[col];		
					switch (curChar) {
						case '#':// rocky cell
							grid[row][col] = new Cell(CellType.ROCKY, 0);
							if (row != 0 || row != WORLDSIZE-1 || col != 0 || col != WORLDSIZE-1) {
								rockCount -= 1;
								if (rockCount < 0) throw new Exception("Limit reached! World must contain 14 rocks. (Excluding world's perimeters)");
							}
							break;
						case '.':// clear cell
							grid[row][col] = new Cell(CellType.CLEAR, 0);
							break;
						case '+':// red anthill
							grid[row][col] = new Cell(CellType.CLEAR, 0);
							Ant ant1 = new Ant(antCount, TeamColor.RED);
							grid[row][col].setAnt(ant1);
							ants.add(ant1);
							antCount += 1;
							break;
						case '-':// black anthill
							grid[row][col] = new Cell(CellType.CLEAR, 0);
							Ant ant2 = new Ant(antCount, TeamColor.BLACK);
							grid[row][col].setAnt(ant2);
							ants.add(ant2);
							antCount += 1;
							break;
						case '1':// 1 food particle
							grid[row][col] = new Cell(CellType.CLEAR, 1);
							break;
						case '2':// 2 food particles
							grid[row][col] = new Cell(CellType.CLEAR, 2);
							break;
						case '3':// 3 food particles
							grid[row][col] = new Cell(CellType.CLEAR, 3);
							break;
						case '4':// 4 food particles
							grid[row][col] = new Cell(CellType.CLEAR, 4);
							break;
						case '5':// 5 food particles
							grid[row][col] = new Cell(CellType.CLEAR, 5);
							break;
						case '6':// 6 food particles
							grid[row][col] = new Cell(CellType.CLEAR, 6);
							break;
						case '7':// 7 food particles
							grid[row][col] = new Cell(CellType.CLEAR, 7);
							break;
						case '8':// 8 food particles
							grid[row][col] = new Cell(CellType.CLEAR, 8);
							break;
						case '9':// 9 food particles
							grid[row][col] = new Cell(CellType.CLEAR, 9);
							break;
						default:
							throw new Exception("Error when creating a new cell.");
					} // end: switch	
					
					/* Check if perimeters are valid. Expected ROCKY cells. */
					if (row == 0 || row == WORLDSIZE-1 || col == 0 || col == WORLDSIZE-1) {
						if (grid[row][col].getState() != CellType.ROCKY) throw new Exception("Invalid perimeter. ROCKY cell required at " + row + " " + col);
					}	
					
				} // end: for col	
				row += 1;
			} // end: while
			if (row != WORLDSIZE)  throw new Exception("Invalid row size.");
		} catch (Exception e) {
			System.err.println("Caught Exception: Error when initializing a grid of cell objects.");
		}		
		if (rockCount > 0) throw new Exception("Limit not reached! World must contain 14 rocks to be valid. (Excluding world's perimeters)");
		validateBlobsOfFood(grid);
		validateRedAnthill(grid);
		validateBlackAnthill(grid);
		return new World(grid, ants.toArray());
	}
	
	/**
	 * Validate blobs of food in a world. 
	 * - check that food blob consists off corect cells (cells of the same value)
	 * - check that food blob is of size 5x5 cells
	 * - check that there are 11 food blobs
	 * @param grid A world to be checked 
	 */
	public void validateBlobsOfFood(Cell[][] grid) {
		int foodBlobCount = 11;
		for(int r = 0; r < WORLDSIZE; r++ ) {
			for(int c = 0; c < WORLDSIZE; c++) {
				if(grid[r][c] == '1' || '2' || '3' || '4' || '5' || '6' || '7' || '8' || '9' ) {
					for(int rr = r+1; rr < r+4; rr++) {
						for(int cc = c+1; cc < c+4; cc++) {
							if(grid[rr][cc] != grid[r][c]) throw new Exception("Blob of food has invalid cell(s).");
						} // end: cc
					} // end: rr
					foodBlobCount -= 1;
					if (foodBlobCount < 0) throw new Exception("Limit reached! World must contain 11 blobs of food.")
				} // end: check if cell contains food particle
			} // end: c
		} // end: r
		if(foodBlobCount > 0) throw new Exception("Limit not reached! World must contain 11 food blobs to be valid");
	}
	
	/**
	 * Validate red anthill in a world. 
	 * - check if dimentions are correct (hexagon with sides of length 7)
	 * - check if red anthill consists of only red anthill cells 
	 * - check there is only 1 red anthill in the world.
	 */
	public void validateRedAnthill(Cell[][] grid) {
		int redAnthillCount = 1;
		for(int r = 0; r < WORLDSIZE; r++ ) {
			for(int c = 0; c < WORLDSIZE; c++) {
				for(int y = 0; y<=18; y++){
					for(int x = 0; x<=18; x++) {
						if(y<=12 && x+y>=6 && x-y<13 ){
							//Top and centre of the anthill
							if(grid[posX[0]+x][posY[0]+y] != '+') throw new Exception("Expected red anthill cell but found other cell type.");
						}else if(y>=12 && y-x <= 12 && x+y<=30){
							//Bottom of the anthill
							if(grid[posX[0]+x][posY[0]+y] != '+') throw new Exception("Expected red anthill cell but found other cell type.");
						}
					}
				} // end: check if a cell is a part of red anthill
				if((redAnthillCount-1) < 0) {
					throw new Exception("Limit reached! World must contain 1 red anthill.")
				} else {
					redAnthillCount -= 1;
				}
			} // end: c
		} // end: r
		if (redAnthillCount > 0) throw new Exception("Limit not reached! World must contain 1 red anthill to be valid");
	}

	/**
	 * Validate black anthill in a world. 
	 * - check if dimentions are correct (hexagon with sides of length 7)
	 * - check if black anthill consists of only black anthill cells 
	 * - check there is only 1 black anthill in the world.
	 */
	public void validateBlackAnthill(Cell[][] grid) {
		int blackAnthillCount = 1;
		for(int r = 0; r < WORLDSIZE; r++ ) {
			for(int c = 0; c < WORLDSIZE; c++) {
				for(int y = 0; y<=18; y++){
					for(int x = 0; x<=18; x++) {
						if(y<=12 && x+y>=6 && x-y<13 ){
							//Top and centre of the anthill
							if(grid[posX[0]+x][posY[0]+y] != '+') throw new Exception("Expected black anthill cell but found other cell type.");
						}else if(y>=12 && y-x <= 12 && x+y<=30){
							//Bottom of the anthill
							if(grid[posX[0]+x][posY[0]+y] != '+') throw new Exception("Expected black anthill cell but found other cell type.");
						}
					}
				} // end: check if a cell is a part of black anthill
				if((blackAnthillCount-1) < 0) {
					throw new Exception("Limit reached! World must contain 1 black anthill.")
				} else {
					blackAnthillCount -= 1;
				}
			} // end: c
		} // end: r
		if (blackAnthillCount > 0) throw new Exception("Limit not reached! World must contain 1 black anthill to be valid");
	}
	
	/**
	 * Print world
	 * @param w World to print
	 */
	public void printWorld(World w){
		 Cell[][] grid = w.getCells();
		 for(int r=0; r<WORLDSIZE; r++) {
			 for(int c=0; c<WORLDSIZE; c++) {
				System.out.print(grid[r][c]+" ");	
			 }
			 System.out.println("");
		 }
	}

}
