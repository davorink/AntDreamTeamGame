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
    private int WORLDSIZE = 150;
    /**
     * Default constructor.
     * @throws Exception 
     */
    public WorldEngine() {  
    }
     
    /**
     * Load world from a specified file. 
     * @param fname Name of file to load the world from
     * @throws WorldEngineException When problem loading the World
     * @throws InvalidValueException If problems with ants
     */
    public World loadWorld(String fname) throws Exception { 
    		return loadWorld(fname,false);
    }
    
    /**
     * Load world from a specified file. 
     * @param fname Name of file to load the world from
     * @param testingTiny True if we are testing the tiny.world
     * @throws Exception
     */
    public World loadWorld(String fname, boolean testingTiny) throws Exception { // throws Exception
    	if(testingTiny)WORLDSIZE = 10;
        FileReader fr;
        try {
            fr = new FileReader(fname);
            bfr = new BufferedReader(fr);
        } catch (FileNotFoundException e1) {
            throw new WorldEngineException("Error reading the World file: "+e1.getStackTrace());
        }
         
        String line;
        char curChar;
        ArrayList<Ant> ants = new ArrayList<Ant>(); 
        int rockCount = 14;
        int foodCellCount = 275;
        int redAnthillCellCount = 163;
        int blackAnthillCellCount = 163;
         
        try { // Check if x and y values are correct
            x = Integer.parseInt(bfr.readLine());
            if (x != WORLDSIZE && testingTiny) throw new WorldEngineException("World's x dimention is not valid.");
            y = Integer.parseInt(bfr.readLine());
            if (y != WORLDSIZE && testingTiny) throw new WorldEngineException("World's y dimention is not valid.");
        } catch (IOException e) {
            throw new WorldEngineException("Error reading x and y variables from file.");
        }catch (NumberFormatException e) {
            throw new WorldEngineException("Error parsing x and y variables.");
        }
         
        grid = new Cell[x][y];
         
        try {
            int antCount = 0;   
            int col = 0;
            /* While there is a line to read */
            while((line = bfr.readLine()) != null) {

            	if(line.charAt(0)==' ')line=line.substring(1);
                String[] curLineStr = line.split(" ");
                /* change String array to char array */
                char[] curLine = new char[curLineStr.length];
                for(int j = 0; j < curLineStr.length; j++) {
                    curLine[j] = curLineStr[j].charAt(0);
                }
                 
                if (curLine.length != WORLDSIZE) throw new WorldEngineException("Invalid row size at row " + col);
                /* For each character create appropriate cell */
                for(int row = 0; row < curLine.length; row++) {
                    curChar = curLine[row];     
                    switch (curChar) {
                        case '#':// rocky cell
                            grid[row][col] = new Cell(CellType.ROCKY, 0);
                            if (row != 0 && row != WORLDSIZE-1 && col != 0 && col != WORLDSIZE-1) {
                                rockCount -= 1;
                                if (rockCount < 0) throw new WorldEngineException("Limit reached! World must contain 14 rocks. (Excluding world's perimeters)");
                            }
                            break;
                        case '.':// clear cell
                            grid[row][col] = new Cell(CellType.CLEAR, 0);
                            break;
                        case '+':// red anthill
                            grid[row][col] = new Cell(CellType.CLEAR, 0);
                            Ant ant1 = new Ant(antCount, TeamColor.RED);
                            grid[row][col].setAnt(ant1);
                            grid[row][col].setAnthillColor(TeamColor.RED);
                            ants.add(ant1);
                            antCount += 1;
                            redAnthillCellCount -= 1;
                            break;
                        case '-':// black anthill
                            grid[row][col] = new Cell(CellType.CLEAR, 0);
                            Ant ant2 = new Ant(antCount, TeamColor.BLACK);
                            grid[row][col].setAnt(ant2);
                            grid[row][col].setAnthillColor(TeamColor.BLACK);
                            ants.add(ant2);
                            antCount += 1;
                            blackAnthillCellCount -= 1;
                            break;
                        case '1':// 1 food particle
                            grid[row][col] = new Cell(CellType.CLEAR, 1);
                            foodCellCount -= 1;
                            break;
                        case '2':// 2 food particles
                            grid[row][col] = new Cell(CellType.CLEAR, 2);
                            foodCellCount -= 1;
                            break;
                        case '3':// 3 food particles
                            grid[row][col] = new Cell(CellType.CLEAR, 3);
                            foodCellCount -= 1;
                            break;
                        case '4':// 4 food particles
                            grid[row][col] = new Cell(CellType.CLEAR, 4);
                            foodCellCount -= 1;
                            break;
                        case '5':// 5 food particles
                            grid[row][col] = new Cell(CellType.CLEAR, 5);
                            foodCellCount -= 1;
                            break;
                        case '6':// 6 food particles
                            grid[row][col] = new Cell(CellType.CLEAR, 6);
                            foodCellCount -= 1;
                            break;
                        case '7':// 7 food particles
                            grid[row][col] = new Cell(CellType.CLEAR, 7);
                            foodCellCount -= 1;
                            break;
                        case '8':// 8 food particles
                            grid[row][col] = new Cell(CellType.CLEAR, 8);
                            foodCellCount -= 1;
                            break;
                        case '9':// 9 food particles
                            grid[row][col] = new Cell(CellType.CLEAR, 9);
                            foodCellCount -= 1;
                            break;
                        default:
                            throw new WorldEngineException("Error creating a new cell.");
                    } // end: switch    
                     
                    /* Check if perimeters are valid. Expected ROCKY cells. */
                    if (row == 0 || row == WORLDSIZE-1 || col == 0 || col == WORLDSIZE-1) {
                        if (grid[row][col].getState() != CellType.ROCKY) 
                            throw new WorldEngineException("Invalid perimeter. ROCKY cell required at " + row + " " + col);
                    }   
                        
                } // end: for col   
                col += 1;
            } // end: while
            if (col != WORLDSIZE) throw new WorldEngineException("Invalid col size: " + col);
        } catch (IOException e) {
            throw new WorldEngineException("Caught Exception: Error when initializing a grid of cell objects.\n" + e.getMessage());
        }       
        if (rockCount > 0 && !testingTiny) throw new WorldEngineException("Limit not reached! World must contain 14 rocks to be valid. (Excluding world's perimeters)");
        if(!testingTiny)validateBlobsOfFood(grid);
        if(!testingTiny)validateRedAnthill(grid);
        if(!testingTiny)validateBlackAnthill(grid);
        if (foodCellCount != 0 && !testingTiny) {
            throw new WorldEngineException("Expected a total of 275 food particles but found " + (275 - foodCellCount) + " cells");
        }
        if (redAnthillCellCount != 0 && !testingTiny) {
            throw new WorldEngineException("Expected a total of 163 red anthill cells but found " + (163 - redAnthillCellCount) + " cells");
        }
        if (blackAnthillCellCount != 0 && !testingTiny) {
            throw new WorldEngineException("Expected a total of 163 black anthill cells but found " + (163 - blackAnthillCellCount) + " cells");
        }
        return new World(grid, ants.toArray(new Ant[0]));
    }
     
    /**
     * Validate blobs of food in a world. 
     * - check that food blob consists off corect cells (cells of the same value)
     * - check that food blob is of size 5x5 cells
     * - check that there are 11 food blobs
     * @param grid A world to be checked 
     * @throws WorldEngineException When error validating blobs of food
     */
    public void validateBlobsOfFood(Cell[][] grid) throws WorldEngineException {
        int foodBlobCount = 11;
        int coordsCount = 0;
        int[][] coords = new int[foodBlobCount][2];
        for(int r = 0; r < WORLDSIZE; r++ ) {
            for(int c = 0; c < WORLDSIZE; c++) { 
                if(grid[r][c].getFoodAmount() > 0) {
                    boolean checkFarEnough = true;
                    for(int x = 0; x < coords.length; x++){
                        if(!farEnough(r, c, coords[x][0], coords[x][1], 6) && coords[x][0]!=0 && coords[x][1]!=0){
                            checkFarEnough = false;
                            break;
                        }
                    }
                    if(checkFarEnough){
                        coords[coordsCount][0] = r;
                        coords[coordsCount][1] = c;
                        coordsCount += 1;
                        for(int rr = r; rr < r+4; rr++) {
                            for(int cc = c; cc < c+5; cc++) {
                                if(grid[rr][cc].getFoodAmount() != grid[r][c].getFoodAmount()){
                                    throw new WorldEngineException("Blob of food has invalid cell(s).");
                                    }
                                } // end: cc
                        } // end: rr
                        foodBlobCount -= 1;
                    }       
                    if (foodBlobCount < 0) throw new WorldEngineException("Limit reached! World must contain 11 blobs of food.");
                } // end: check if cell contains food particle
            } // end: c
        } // end: r
        if(foodBlobCount > 0) throw new WorldEngineException("Limit not reached! World must contain 11 food blobs to be valid");
    }
     
    /**
     * Validate red anthill in a world. 
     * - check if dimentions are correct (hexagon with sides of length 7)
     * - check if red anthill consists of only red anthill cells 
     * - check there is only 1 red anthill in the world.
     * @throws WorldEngineException when error validating Red anthill
     */
    public void validateRedAnthill(Cell[][] grid) throws WorldEngineException {
        int redAnthillCount = 1;
        int coordsCount = 0;
        int[][] coords = new int[redAnthillCount][2];
        coords[0][0] = -50;
        coords[0][1] = -50;
        for(int r = 0; r < WORLDSIZE; r++ ) {
            for(int c = 0; c < WORLDSIZE; c++) {
                if(grid[r][c].getAnthillColor() == TeamColor.RED) {
                    int boundingBoxX = r;
                    int boundingBoxY = c-6;
                    boolean checkFarEnough = true;
                    for(int x = 0; x < coords.length; x++){
                        if(!farEnough(r, c, coords[x][0], coords[x][1], 25)){
                            checkFarEnough = false;
                            break;
                        }
                    }
                    if(checkFarEnough){
                        coords[coordsCount][0] = r;
                        coords[coordsCount][1] = c;
                        coordsCount += 1;
                         
                         
                        for(int y = 0; y<13; y++){
                            for(int x = 0; x<19; x++){
 
                                    if(y<7 && x+y>5 && x-y<13  ){
                                        //Top and centre of the anthill
                                        if(grid[boundingBoxX+x][boundingBoxY+y].getAnthillColor() != TeamColor.RED){
                                            throw new WorldEngineException("Expected red anthill cell but found other cell type. A) " + (boundingBoxX+x) + ":" + (boundingBoxY+y) + " " + boundingBoxX + ":" + boundingBoxY + " " + grid[boundingBoxX+x][boundingBoxY+y].getAnthillColor());
                                        }
                                    }else if(y>=7 && y-x<7 && x+y<25){
                                        //Bottom of the anthill
                                        if(grid[boundingBoxX+x][boundingBoxY+y].getAnthillColor() != TeamColor.RED){
                                            throw new WorldEngineException("Expected red anthill cell but found other cell type. B) " + (boundingBoxX+x) + ":" + (boundingBoxY+y) + " " + boundingBoxX + ":" + boundingBoxY + " " + grid[boundingBoxX+x][boundingBoxY+y].getAnthillColor());
                                        }
                                    }
                            }
                        }
                         
                        redAnthillCount -= 1;
                    }
                }
                if(redAnthillCount < 0) {
                    throw new WorldEngineException("Limit reached! World must contain 1 red anthill.");
                }
            } // end: c
        } // end: r
        if (redAnthillCount > 0) throw new WorldEngineException("Limit not reached! World must contain 1 red anthill to be valid");
    }
 
    /**
     * Validate black anthill in a world. 
     * - check if dimentions are correct (hexagon with sides of length 7)
     * - check if black anthill consists of only black anthill cells 
     * - check there is only 1 black anthill in the world.
     * @throws WorldEngineException when error validating black anthill
     */
    public void validateBlackAnthill(Cell[][] grid) throws WorldEngineException {
        int blackAnthillCount = 1;
        int coordsCount = 0;
        int[][] coords = new int[blackAnthillCount][2];
        coords[0][0] = -50;
        coords[0][1] = -50;
        for(int r = 0; r < WORLDSIZE; r++ ) {
            for(int c = 0; c < WORLDSIZE; c++) {
                if(grid[r][c].getAnthillColor() == TeamColor.BLACK) {
                    int boundingBoxX = r;
                    int boundingBoxY = c-6;
                    boolean checkFarEnough = true;
                    for(int x = 0; x < coords.length; x++){
                        if(!farEnough(r, c, coords[x][0], coords[x][1], 25)){
                            checkFarEnough = false;
                            break;
                        }
                    }
                    if(checkFarEnough){
                        coords[coordsCount][0] = r;
                        coords[coordsCount][1] = c;
                        coordsCount += 1;
                        for(int y = 0; y<13; y++){
                            for(int x = 0; x<19; x++){
                                    if(y<7 && x+y>5 && x-y<13  ){
                                        //Top and centre of the anthill
                                        if(grid[boundingBoxX+x][boundingBoxY+y].getAnthillColor() != TeamColor.BLACK) 
                                            throw new WorldEngineException("Expected black anthill cell but found other cell type.");
                                    }else if(y>=7 && y-x<7 && x+y<25){
                                        //Bottom of the anthill
                                        if(grid[boundingBoxX+x][boundingBoxY+y].getAnthillColor() != TeamColor.BLACK) 
                                            throw new WorldEngineException("Expected black anthill cell but found other cell type.");
                                    }
                            }
                        } // end: check if a cell is a part of black anthill
                        blackAnthillCount -= 1;
                    }
                }
                if(blackAnthillCount < 0) {
                    throw new WorldEngineException("Limit reached! World must contain 1 black anthill.");
                }
            } // end: c
        } // end: r
        if (blackAnthillCount > 0) 
            throw new WorldEngineException("Limit not reached! World must contain 1 black anthill to be valid");
    }
     
    /**
     * Calculates euclidean distance between points (x1,y1) and (x2,y2) 
     * and returns whether or not they are far enough from each other
     * @param x1 Point1's x
     * @param y1 Point1's y
     * @param x2 Point2's x
     * @param y2 Point2's y
     * @param wantedDistance How far away they should be from one another.
     * @return True if points are far enough from one another.
     */
    protected boolean farEnough(int x1, int y1, int x2, int y2, int wantedDistance){
        if(Math.sqrt(Math.pow((x1-x2),2) + Math.pow((y1-y2),2))<=wantedDistance){
            return false;
        }
        return true;
    }
 
    /**
     * Generates and returns a valid new World file.
     * @param filename Path and Filename where the world file will be stored.
     * @return filename of the new world file
     * @throws WorldEngineException When error saving the file
     */
    public String generateNewWorldFile(String filename) throws WorldEngineException {
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
                posX[co] = (int)(2+Math.random()*(WORLDSIZE-22)); // 2 - 129
                posY[co] = (int)(2+Math.random()*(WORLDSIZE-22)); // 2 - 129
                  
            }
              
            validAnthills = true;
            //Calculate euclidean distance between the anthills
            //If more than 25 then anthils are not touching.
            for(int c1 = 0; c1 < anthillColors.length; c1++){
                for(int c2 = 0; c2 < anthillColors.length; c2++){
                    if(c1!=c2 && Math.sqrt(Math.pow((posX[c1]-posX[c2]),2) + Math.pow((posY[c1]-posY[c2]),2))<=25){
                        validAnthills = false;
                    }
                }   
            }
        }while(validAnthills == false);
          
        //Draw anthills in the cells
        for(int anthillColor = 0; anthillColor < anthillColors.length; anthillColor++){
            for(int y = 0; y<13; y++){
                for(int x = 0; x<19; x++){
                    if(y<7 && x+y>5 && x-y<13  ){
                        //Top and centre of the anthill
                        cells[posX[anthillColor]+x][posY[anthillColor]+y]=anthillColors[anthillColor];
                    }else if(y>=7 && y-x<7 && x+y<25){
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
                posXfood[co] = (int)(2+Math.random()*(WORLDSIZE-7)); // 2 - 144
                posYfood[co] = (int)(2+Math.random()*(WORLDSIZE-7)); // 2 - 144
            }
            validBlobs = true;
            //Calculate euclidean distance between the food blobs
            for(int c1 = 0; c1 < numFoodBlobs; c1++){
                for(int c2 = 0; c2 < numFoodBlobs; c2++){
                    if(c1!=c2 && Math.sqrt(Math.pow((posXfood[c1]-posXfood[c2]),2) + Math.pow((posYfood[c1]-posYfood[c2]),2))<=8){
                        validBlobs = false;
                    }
                }   
            }
              
            //Calculate Euclidean distances between food blobs and anthills
            for(int c1 = 0; c1 < numFoodBlobs; c1++){
                for(int c2 = 0; c2 < anthillColors.length; c2++){
                    if(Math.sqrt(Math.pow((posXfood[c1]-posX[c2]),2) + Math.pow((posYfood[c1]-posY[c2]),2))<=25){
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
                posXrock[co] = (int)(2+Math.random()*(WORLDSIZE-3)); // 2 - 194
                posYrock[co] = (int)(2+Math.random()*(WORLDSIZE-3)); // 2 - 194
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
                    if(Math.sqrt(Math.pow((posXrock[c1]-posX[c2]),2) + Math.pow((posYrock[c1]-posY[c2]),2))<=25){
                        validRocks = false;
                    }
                }   
            }
              
            //Calculate Euclidean distances between rocks and food
            for(int c1 = 0; c1 < numRocks; c1++){
                for(int c2 = 0; c2 < numFoodBlobs; c2++){
                    if(Math.sqrt(Math.pow((posXrock[c1]-posXfood[c2]),2) + Math.pow((posYrock[c1]-posYfood[c2]),2))<=7){
                        validRocks = false;
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
            File file = new File(filename);
      
            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
      
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(worldStr.toString());
            bw.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            throw new WorldEngineException("World successfully generated, but error saving the file! "+e.toString());
        }
    }
}
 
/**
 * Class WorldEngineException is used to report exceptions in the World
 * @author D Kopic
 */
@SuppressWarnings("serial")
class WorldEngineException extends Exception {
    public String msg;
     
    public WorldEngineException(String _msg){
        super(_msg);
        msg = _msg;
    }
}