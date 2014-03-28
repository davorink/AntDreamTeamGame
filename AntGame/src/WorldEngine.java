import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class WorldEngine {
	static BufferedReader bfr;
	private static int x;
	private static int y;
	private static Cell[][] grid= new Cell[x][y];
	private static String file = "C:/Users/Hexeasaurus/Desktop/test.txt/";
	
	public static void main(String args[]) throws IOException{
		WorldEngine w = new WorldEngine();
		//loadWorld(file);
		generateNewWorldFile();
	}
	
	public WorldEngine(){
		
	}
	
	/**
	 * Load World
	 * @param fname name of file to load the world from
	 * @throws IOException
	 */
	public static void loadWorld(String fname) throws IOException {
		FileReader fr = new FileReader(fname);
		bfr = new BufferedReader(fr);
		String line;
		String curChar;
		x = 10;
		y = 10;
		
		while((line = bfr.readLine()) != null) {
			String[] curLine = line.split(" ");
			System.out.println(line);
			
			for(int row = 0; row < y; row++) {
				for(int col = 0; col < curLine.length; col++) {
					curChar = curLine[col];		
					switch (curChar) {
						case "#":// rocky cell
							grid[row][col] = new Cell(CellType.ROCKYCELL, false, 0, false, false);
							break;
						case ".":// clear cell
							grid[row][col] = new Cell(CellType.CLEARCELL, false, 0, false, false);
							break;
						case "+":// red anthill
							grid[row][col] = new Cell(CellType.CLEARCELL, false, 0, true, false);
							break;
						case "-":// black anthill
							grid[row][col] = new Cell(CellType.CLEARCELL, false, 0, false, true);
							break;
						case "1":// 1 food particle
							grid[row][col] = new Cell(CellType.CLEARCELL, false, 1, false, false);
							break;
						case "2":// 2 food particles
							grid[row][col] = new Cell(CellType.CLEARCELL, false, 2, false, false);
							break;
						case "3":// 3 food particles
							grid[row][col] = new Cell(CellType.CLEARCELL, false, 3, false, false);
							break;
						case "4":// 4 food particles
							grid[row][col] = new Cell(CellType.CLEARCELL, false, 4, false, false);
							break;
						case "5":// 5 food particles
							grid[row][col] = new Cell(CellType.CLEARCELL, false, 5, false, false);
							break;
						case "6":// 6 food particles
							grid[row][col] = new Cell(CellType.CLEARCELL, false, 6, false, false);
							break;
						case "7":// 7 food particles
							grid[row][col] = new Cell(CellType.CLEARCELL, false, 7, false, false);
							break;
						case "8":// 8 food particles
							grid[row][col] = new Cell(CellType.CLEARCELL, false, 8, false, false);
							break;
						case "9":// 9 food particles
							grid[row][col] = new Cell(CellType.CLEARCELL, false, 9, false, false);
							break;
						default:
							grid[row][col] = new Cell(CellType.CLEARCELL, false, 0, false, false);
							break;
					}
				}
			}
		}
	}
	
	/**
	 * Check world's validity
	 */
	public static boolean validateWorld(Cell[][] grid) {
		
		return false;
	}
	
	/**
	 * Parse world
	 */
	
	/**
	 * Generate new world file
	 */
	public static void generateNewWorldFile() {
		try {
			String content = "This is the content to write into file";
			 
			File file = new File("C:/Users/Hexeasaurus/Desktop/test2.txt");
	
			// if file does not exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
	
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
	
			System.out.println("New file created.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
