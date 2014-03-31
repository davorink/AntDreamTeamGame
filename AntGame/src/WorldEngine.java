/**
 * A class to represent world engine
 * @author K Ratusznik
 * @version 31/03/2014
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class WorldEngine {
	private BufferedReader bfr;
	private int x;
	private int y;
	private Cell[][] grid;
	private String file = "//smbhome.uscs.susx.ac.uk/kr210/Downloads/1.world.txt/";
	private final int WORLDSIZE = 150;
	private World world;
	
	/**
	 * Default constructor.
	 */
	public WorldEngine(String fname){	
		world = loadWorld(fname);
		printWorld(world);
	}
	
	public void printWorld(World w){
		 Cell[][] cells = w.getCells();
		 for(int r=0; r<WORLDSIZE; r++) {
			 for(int c=0; c<WORLDSIZE; c++) {
				System.out.print(cells[row][col]+" ");	
			 }
			 System.out.println("");
		 }
	}
	
	public static void main(String[] args) {
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
		String curChar;
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
				char[] curLine = line.split(" ");
				if (curLine.length() != WORLDSIZE)  throw new Exception("Invalid column size at row " + row);
				/* For each character create appropriate cell */
				for(int col = 0; col < curLine.length; col++) {
					curChar = curLine[col];		
					switch (curChar) {
						case '#':// rocky cell
							grid[row][col] = new Cell(cellType.ROCKY, 0);
							break;
						case '.':// clear cell
							grid[row][col] = new Cell(cellType.CLEAR, 0);
							break;
						case '+':// red anthill
							grid[row][col] = new Cell(cellType.CLEAR, 0);
							Ant ant = new Ant(antCounter, color.RED);
							grid[row][col].setAnt(ant);
							ants.add(ant);
							antCounter += 1;
							break;
						case '-':// black anthill
							grid[row][col] = new Cell(cellType.CLEAR, 0);
							Ant ant = new Ant(antCounter, color.BLACK);
							grid[row][col].setAnt(ant);
							ants.add(ant);
							antCounter += 1;
							break;
						case '1':// 1 food particle
							grid[row][col] = new Cell(cellType.CLEAR, 1);
							break;
						case '2':// 2 food particles
							grid[row][col] = new Cell(cellType.CLEAR, 2);
							break;
						case '3':// 3 food particles
							grid[row][col] = new Cell(cellType.CLEAR, 3);
							break;
						case '4':// 4 food particles
							grid[row][col] = new Cell(cellType.CLEAR, 4);
							break;
						case '5':// 5 food particles
							grid[row][col] = new Cell(cellType.CLEAR, 5);
							break;
						case '6':// 6 food particles
							grid[row][col] = new Cell(cellType.CLEAR, 6);
							break;
						case '7':// 7 food particles
							grid[row][col] = new Cell(cellType.CLEAR, 7);
							break;
						case '8':// 8 food particles
							grid[row][col] = new Cell(cellType.CLEAR, 8);
							break;
						case '9':// 9 food particles
							grid[row][col] = new Cell(cellType.CLEAR, 9);
							break;
						default:
							throw new Exception("Error when creating a new cell.");
					} // end: switch						
				} // end: for col		
				
				/* Check if perimeters are valid. Expected ROCKY cells. */
				if (row == 0 || row == WORLDSIZE-1 || col == 0 || col == WORLDSIZE-1) {
					if (grid[row][col] != '#') throw new Exception("Invalid perimeter. ROCKY cell required at " + row + " " + col);
				}	
				
				row += 1;
			} // end: while
			if (row != WORLDSIZE)  throw new Exception("Invalid row size.");
		} catch (Exception e) {
			System.err.println("Caught Exception: Error when initializing a grid of cell objects.");
		}
		
		return new World(grid, ants.toArray());
	}
	
	/**
	 * Check world's validity.
	 */
	public boolean validateWorld(Cell[][] grid) {
		for 
		
		return false;
	}
	
	/**
	 * Parse world.
	 */
	
	/**
	 * Generate new world file.
	 */
	public void generateNewWorldFile() {
		try {
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
