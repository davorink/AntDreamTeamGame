/**
 * A class to represent world engine
 * @author K Ratusznik & D Kopic
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
	 * @throws Exception 
	 */
	public WorldEngine() {	
	}
	
	/**
	 * Load world from a specified file. 
	 * @param fname Name of file to load the world from
	 * @throws Exception 
	 */
	public World loadWorld(String fname) throws Exception {
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
		return new World(grid, (Ant[]) ants.toArray());
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
				if(grid[r][c].getFoodAmount() > 0) {
					for(int rr = r+1; rr < r+4; rr++) {
						for(int cc = c+1; cc < c+4; cc++) {
							if(grid[rr][cc] != grid[r][c]) // throw new Exception("Blob of food has invalid cell(s).");
								System.out.println("Blob of food has invalid cell(s).");
							} // end: cc
					} // end: rr
					foodBlobCount -= 1;
					if (foodBlobCount < 0) // throw new Exception("Limit reached! World must contain 11 blobs of food.");
					System.out.println("Limit reached! World must contain 11 blobs of food.");
				} // end: check if cell contains food particle
			} // end: c
		} // end: r
		if(foodBlobCount > 0) // throw new Exception("Limit not reached! World must contain 11 food blobs to be valid");
			System.out.println("Limit not reached! World must contain 11 food blobs to be valid");
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
				if(grid[r][c].getAnthillColor() == TeamColor.RED) {
					int boundingBoxX = r;
					int boundingBoxY = c-6;
					for(int y = 0; y<=18; y++){
						for(int x = 0; x<=18; x++) {
							if(y<=12 && x+y>=6 && x-y<13 ){
								//Top and centre of the anthill
								if(grid[boundingBoxX+x][boundingBoxY+y].getAnthillColor() != TeamColor.RED) // throw new Exception("Expected red anthill cell but found other cell type.");
									System.out.println("Expected red anthill cell but found other cell type.");
							}else if(y>=12 && y-x <= 12 && x+y<=30){
								//Bottom of the anthill
								if(grid[boundingBoxX+x][boundingBoxY+y].getAnthillColor() != TeamColor.RED) // throw new Exception("Expected red anthill cell but found other cell type.");
									System.out.println("Expected red anthill cell but found other cell type.");
							}
						}
					} // end: check if a cell is a part of red anthill
				}
				if((redAnthillCount-1) < 0) {
					// throw new Exception("Limit reached! World must contain 1 red anthill.")
					System.out.println("Limit reached! World must contain 1 red anthill.");
				} else {
					redAnthillCount -= 1;
				}
			} // end: c
		} // end: r
		if (redAnthillCount > 0) // throw new Exception("Limit not reached! World must contain 1 red anthill to be valid");
			System.out.println("Limit not reached! World must contain 1 red anthill to be valid");
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
				if(grid[r][c].getAnthillColor() == TeamColor.BLACK) {
					int boundingBoxX = r;
					int boundingBoxY = c-6;
					for(int y = 0; y<=18; y++){
						for(int x = 0; x<=18; x++) {
							if(y<=12 && x+y>=6 && x-y<13 ){
								//Top and centre of the anthill
								if(grid[boundingBoxX+x][boundingBoxY+y].getAnthillColor() != TeamColor.BLACK) // throw new Exception("Expected black anthill cell but found other cell type.");
									System.out.println("Expected black anthill cell but found other cell type.");
							}else if(y>=12 && y-x <= 12 && x+y<=30){
								//Bottom of the anthill
								if(grid[boundingBoxX+x][boundingBoxY+y].getAnthillColor() != TeamColor.BLACK) // throw new Exception("Expected black anthill cell but found other cell type.");
									System.out.println("Expected black anthill cell but found other cell type.");
							}
						}
					} // end: check if a cell is a part of black anthill
				}
				if((blackAnthillCount-1) < 0) {
					// throw new Exception("Limit reached! World must contain 1 black anthill.");
					System.out.println("Limit reached! World must contain 1 black anthill.");
				} else {
					blackAnthillCount -= 1;
				}
			} // end: c
		} // end: r
		if (blackAnthillCount > 0) // throw new Exception("Limit not reached! World must contain 1 black anthill to be valid");
		System.out.println("Limit not reached! World must contain 1 black anthill to be valid");
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
	
	
	/**
	 * Generates and returns a valid new World file.
	 * @return filename of the new world file
	 */
	public String generateNewWorldFile() {
		char[][] cells = new char[WORLDSIZE][WORLDSIZE];
		char[] anthillColors = {'+','-'};
		int numFoodBlobs = 11;
		int numRocks = 14;
		
		// *** Generate perimeter rocks and all other cells are clear.
		for(int y = 0; y < WORLDSIZE; y++){
			for(int x = 0; x < WORLDSIZE; x++){
				if(y == 0 || y == WORLDSIZE-1 || x==0 || x==WORLDSIZE-1 ){
					//Perimiter rocks
					cells[y][x] = '#';
				}else{
					//All other cells
					cells[y][x] = '.';
				}
			}	
		}
		
		
		
		// *** Add Anthills
		boolean validAnthills = true;
		int[] posX = new int[anthillColors.length];
		int[] posY = new int[anthillColors.length];

		//Get valid top-left coordinates of the anthill-bounding box.
		do{
			for(int co = 0; co < anthillColors.length; co++){
				posX[co] = (int)(2+Math.random()*(150-22)); // 2 - 129
				posY[co] = (int)(2+Math.random()*(150-22)); // 2 - 129
				
			}
			
			validAnthills = true;
			//Calculate euclidean distance between the anthills
			//If more than 28 then anthils are not touching.
			for(int c1 = 0; c1 < anthillColors.length; c1++){
				for(int c2 = 0; c2 < anthillColors.length; c2++){
					if(c1!=c2 && Math.sqrt(Math.pow((posX[c1]-posX[c2]),2) + Math.pow((posY[c1]-posY[c2]),2))<=28){
						validAnthills = false;
					}
				}	
			}
		}while(validAnthills == false);
		
		//Draw anthills in the cells
		for(int anthillColor = 0; anthillColor < anthillColors.length; anthillColor++){
			for(int y = 0; y<=18; y++){
				for(int x = 0; x<=18; x++){
						if(y<=12 && x+y>=6 && x-y<13 ){
							//Top and centre of the anthill
							cells[posX[anthillColor]+x][posY[anthillColor]+y]=anthillColors[anthillColor];
						}else if(y>=12 && y-x <= 12 && x+y<=30){
							//Bottom of the anthill
							cells[posX[anthillColor]+x][posY[anthillColor]+y]=anthillColors[anthillColor];
						}
				}
			}
		}
		
		
		// *** Add blobs of food
		boolean validBlobs = true;
		int[] posXfood = new int[numFoodBlobs];
		int[] posYfood = new int[numFoodBlobs];

		//Get valid top-left coordinates of the food blobs.
		do{
			for(int co = 0; co < numFoodBlobs; co++){
				posXfood[co] = (int)(2+Math.random()*(150-7)); // 2 - 144
				posYfood[co] = (int)(2+Math.random()*(150-7)); // 2 - 144
			}
			validBlobs = true;
			//Calculate euclidean distance between the food blobs
			for(int c1 = 0; c1 < numFoodBlobs; c1++){
				for(int c2 = 0; c2 < numFoodBlobs; c2++){
					if(c1!=c2 && Math.sqrt(Math.pow((posXfood[c1]-posXfood[c2]),2) + Math.pow((posYfood[c1]-posYfood[c2]),2))<=7){
						validBlobs = false;
					}
				}	
			}
			
			//Calculate Euclidean distances between food blobs and anthills
			for(int c1 = 0; c1 < numFoodBlobs; c1++){
				for(int c2 = 0; c2 < anthillColors.length; c2++){
					if(Math.sqrt(Math.pow((posXfood[c1]-posX[c2]),2) + Math.pow((posYfood[c1]-posY[c2]),2))<=28){
						validBlobs = false;
					}
				}	
			}
			
		}while(validBlobs == false);
		
		//Draw food blobs in the cells
		for(int a = 0; a < numFoodBlobs; a++){
			for(int y = 0; y<5; y++){
				for(int x = 0; x<5; x++){
					cells[posXfood[a]+x][posYfood[a]+y]='5';
				}
			}
		}
		
		
		
		// *** Add Rocks
		boolean validRocks = true;
		int[] posXrock = new int[numRocks];
		int[] posYrock = new int[numRocks];

		//Get valid top-left coordinates of the rocks.
		do{
			for(int co = 0; co < numRocks; co++){
				posXrock[co] = (int)(2+Math.random()*(150-3)); // 2 - 194
				posYrock[co] = (int)(2+Math.random()*(150-3)); // 2 - 194
			}
			validRocks = true;
			//Calculate euclidean distance between the rocks
			for(int c1 = 0; c1 < numRocks; c1++){
				for(int c2 = 0; c2 < numRocks; c2++){
					if(c1!=c2 && Math.sqrt(Math.pow((posXrock[c1]-posXrock[c2]),2) + Math.pow((posYrock[c1]-posYrock[c2]),2))<=2){
						validRocks = false;
					}
				}	
			}
			
			//Calculate Euclidean distances between rocks and anthills
			for(int c1 = 0; c1 < numRocks; c1++){
				for(int c2 = 0; c2 < anthillColors.length; c2++){
					if(Math.sqrt(Math.pow((posXrock[c1]-posX[c2]),2) + Math.pow((posYrock[c1]-posY[c2]),2))<=28){
						validRocks = false;
					}
				}	
			}
			
			//Calculate Euclidean distances between rocks and food
			for(int c1 = 0; c1 < numRocks; c1++){
				for(int c2 = 0; c2 < numFoodBlobs; c2++){
					if(Math.sqrt(Math.pow((posXrock[c1]-posXfood[c2]),2) + Math.pow((posYrock[c1]-posYfood[c2]),2))<=7){
						validBlobs = false;
					}
				}	
			}
			
		}while(validRocks == false);
		
		//Draw rocks in the cells
		for(int a = 0; a < numRocks; a++){
			cells[posXrock[a]][posYrock[a]]='#';
		}
		
		
		// Actually write it to file
		String nl = System.getProperty("line.separator");
		StringBuffer worldStr = new StringBuffer();
		worldStr.append(WORLDSIZE+nl+WORLDSIZE+nl);
		for(int y = 0; y < WORLDSIZE; y++){
			for(int x = 0; x < WORLDSIZE; x++){
				worldStr.append(cells[x][y]+" ");
			}	
			worldStr.append(nl);
		}
		
		try {
			//TODO: Generate random filename!
			File file = new File("E:/test2.txt");
	
			// If file does not exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
	
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(worldStr.toString());
			bw.close();
	
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return file.toString();
		
	}
	
	

}
