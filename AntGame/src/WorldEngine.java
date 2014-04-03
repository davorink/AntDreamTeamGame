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
	
	public void printWorld(World w){
		 Cell[][] cells = w.getCells();
		 for(int r=0; r<WORLDSIZE; r++) {
			 for(int c=0; c<WORLDSIZE; c++) {
				System.out.print(cells[r][c]+" ");	
			 }
			 System.out.println("");
		 }
	}
	
	public static void main(String[] args) throws IOException {
		file = "//smbhome.uscs.susx.ac.uk/kr210/Downloads/1.world.txt/";
		WorldEngine pfrr = new WorldEngine(file);
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
		
		try {
			x = Integer.parseInt(bfr.readLine());
			if (x != WORLDSIZE) throw new Exception("World's x dimention is not valid.");
			y = Integer.parseInt(bfr.readLine());
			if (y != WORLDSIZE) throw new Exception("World's y dimention is not valid.");
		} catch (Exception e) {
			System.err.println("Caught Exception: Error when parsing x and y variables.");
		}
		
		grid = new Cell[x][y];
		
		try {
			int antCounter = 0;	
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
							break;
						case '.':// clear cell
							grid[row][col] = new Cell(CellType.CLEAR, 0);
							break;
						case '+':// red anthill
							grid[row][col] = new Cell(CellType.CLEAR, 0);
							Ant ant1 = new Ant(antCounter, color.RED);
							grid[row][col].setAnt(ant1);
							ants.add(ant1);
							antCounter += 1;
							break;
						case '-':// black anthill
							grid[row][col] = new Cell(CellType.CLEAR, 0);
							Ant ant2 = new Ant(antCounter, color.BLACK);
							grid[row][col].setAnt(ant2);
							ants.add(ant2);
							antCounter += 1;
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
		return new World(grid, ants.toArray());
	}
	
	/**
	 * Parse world.
	 */
	
	/**
	 * Generate new world file and save it to the disk.
	 * @return Path to the generated world's file
	 */
	public String generateNewWorldFile() {
		
		int size = 150;
		char[][] cells = new char[size][size];
		
		
		//Generate perimeter rocks and all other cells are clear.
		for(int x = 0; x <= size; x++){
			for(int y = 0; y <= size; y++){
				if(x == 0 || x == size-1 || y==0 || y==size-1 ){
					//Perimiter rocks
					cells[x][y] = '#';
				}else{
					//All other cells
					cells[x][y] = '.';
				}
			}	
		}
		
		//Add Anthills

		boolean validAnthills = false;
		int posX1 = 0;
		int posX2 = 0;
		int posY1 = 0;
		int posY2 = 0;
		//Get valid top-left coordinates of the anthill-bounding box.
		do{
			posX1 = (int)(2+Math.random()*(150-22)); // 2 - 129
			posY1 = (int)(2+Math.random()*(150-22)); // 2 - 129
			
			posX2 = (int)(2+Math.random()*(150-22)); // 2 - 129
			posY2 = (int)(2+Math.random()*(150-22)); // 2 - 129
			
			//Calculate euclidean distance between the anthills
			//If more than 28 then anthils are not touching.
			if(Math.sqrt(Math.pow((posX1-posX2),2) + Math.pow((posY1-posY2),2))>28){
				validAnthills = true;
			}
			
		}while(validAnthill == false);
		
		
		for(int x = 0; x<=18; x++){
			for(int y = 0; y<=18;y++){
				if(y-6<=0)cells[posX1+x][posY1+y]='Z';
			}
		}
		

		
		
		
		try {
			char[] line;
			for (int i = 0; i < )
			String content = "This is the content to write into file";
			 
			File file = new File("C:/Users/Hexeasaurus/Desktop/test2.txt");
	
			// If file does not exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
	
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
	
			System.out.println("New file has been created.");
		} catch (IOException e) {
			System.err.println("Caught IOException: Error when generating a world file.");
		}
	}
}
