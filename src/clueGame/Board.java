package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Board {
	
	// Instance variables
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfig;
	private String setupConfigFile;
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	private static Board theInstance = new Board();
	
	// Default constructor - private because of singleton pattern
	private Board() {
		super();
	}
	
	// getInstance - sets up the one board instance
	public static Board getInstance() {
		return theInstance;
	}
	
	// Initializes the board
	public void initialize() {
		try {
			loadSetupConfig();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	// Loads the setup file and creates the roomMap
	public void loadSetupConfig() throws BadConfigFormatException {
		try {
			File setup = new File("data/" + setupConfigFile); // Read in file
			Scanner mySetup = new Scanner(setup);
			
			while (mySetup.hasNextLine()) {
				String[] line = mySetup.nextLine().split(",");
				
				if (line[0].equals("Room") || line[0].equals("Space")) { // Sets up rooms
					Room room = new Room();
					String roomName = line[1].substring(1);
					room.setName(roomName);
					char character = line[2].charAt(1);
					roomMap.put(character, room);
					
				} else if (!line[0].equals("")) { // Throws exception if room type is wrong
					 String firstTwoChar = line[0].substring(0, 2);
					 
					if (!firstTwoChar.equals("//")) {
						 throw new BadConfigFormatException(setupConfigFile, "room type");
					 }
				}
			}
			mySetup.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	// Loads the layout file and creates the grid
	public void loadLayoutConfig() throws BadConfigFormatException {
		ArrayList<String[]> lines = new ArrayList<String[]>();
		try {
			File layout = new File("data/" +layoutConfig); // Loads in file
			Scanner myLayout = new Scanner(layout);
			while(myLayout.hasNextLine()) {
				String[] line = myLayout.nextLine().split(","); // Splits line into array of strings
				lines.add(line);
			}
			myLayout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//Gets the number of rows and columns
		numRows = lines.size();
		numColumns = lines.get(0).length;
		
		//Creates each cell and adds it to the grid
		grid = new BoardCell[numRows][numColumns];
		for (int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				
				// Throws exception if number of columns is inconsistent
				int rowLength = lines.get(i).length;
				if (rowLength != numColumns) {
					throw new BadConfigFormatException(layoutConfig, "column");
				}
				
				// Creates cell
				String initial = lines.get(i)[j];
				BoardCell cell = new BoardCell(i,j,initial.charAt(0));
				
				// Throws exception if the cell contains an initial not tied to a room
				char cellInitial = cell.getInitial();
				if (roomMap.containsKey(cellInitial) == false) {
					throw new BadConfigFormatException(layoutConfig, "room");
				}
				
				// Sets up door direction
				if (initial.length() > 1) {
					if (initial.charAt(1) == '>'){
						cell.setDoorDirection(DoorDirection.RIGHT);
					}
					else if (initial.charAt(1) == '<'){
						cell.setDoorDirection(DoorDirection.LEFT);
					}
					else if (initial.charAt(1) == '^'){
						cell.setDoorDirection(DoorDirection.UP);
					}
					else if (initial.charAt(1) == 'v'){
						cell.setDoorDirection(DoorDirection.DOWN);
					}
					else if (initial.charAt(1) == '#'){
						Room room = theInstance.getRoom(cellInitial);
						room.setLabelCell(cell);
						cell.setRoomLabel(true);
					}
					else if (initial.charAt(1) == '*'){
						Room room = theInstance.getRoom(cellInitial);
						room.setCenterCell(cell);
						cell.setRoomCenter(true);
					}
					else {
						cell.setSecretPassage(initial.charAt(1));
					}
					
				} else {
					cell.setDoorDirection(DoorDirection.NONE);
				}
				grid[i][j] = cell;
			}
		}
		
	}
	
	public void calcTargets(BoardCell startCell, int moves) {
		
	}
	
	// Getters and setters
	public Set<BoardCell> getAdjList(int row, int col) {
		return new HashSet<BoardCell>();
	}
	
	public Set<BoardCell> getTargets() {
		return new HashSet<BoardCell>();
	}
	
	
	public void setConfigFiles(String layout, String setup) {
		layoutConfig = layout;
		setupConfigFile = setup;
	}
	
	public Room getRoom(char room) {
		return roomMap.get(room);
	}
	
	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}
	
	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}
}
