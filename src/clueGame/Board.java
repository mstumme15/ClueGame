package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Board {
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfig;
	private String setupConfigFile;
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	private static Board theInstance = new Board();
	
	private Board() {
		super();
	}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	// Initializes the board
	public void initialize() {
		loadSetupConfig();
		loadLayoutConfig();
		
	}
	
	// Loads the setup file and creates the roomMap
	public void loadSetupConfig() {
		try {
			File setup = new File("data/" + setupConfigFile);
			Scanner mySetup = new Scanner(setup);
			while(mySetup.hasNextLine()) {
				String[] line = mySetup.nextLine().split(",");
			
				if (line[0].equals("Room") || line[0].equals("Space")) {
					Room room = new Room();
					room.setName(line[1].substring(1));
					char character = line[2].charAt(1);
					roomMap.put(character, room);
				}
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	// Loads the layout file and creates the grid
	public void loadLayoutConfig() {
		ArrayList<String[]> lines = new ArrayList<String[]>();
		try {
			File layout = new File("data/" +layoutConfig);
			Scanner myLayout = new Scanner(layout);
			while(myLayout.hasNextLine()) {
				String[] line = myLayout.nextLine().split(",");
				lines.add(line);
			}
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
				String initial = lines.get(i)[j];
				BoardCell cell = new BoardCell(i,j,initial.charAt(0));
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
						Room room = theInstance.getRoom(initial.charAt(0));
						room.setLabelCell(cell);
						cell.setRoomLabel(true);
					}
					else if (initial.charAt(1) == '*'){
						Room room = theInstance.getRoom(initial.charAt(0));
						room.setCenterCell(cell);
						cell.setRoomCenter(true);
					}
					else {
						cell.setSecretPassage(initial.charAt(1));
					}
					
				}
				grid[i][j] = cell;
			}
		}
		
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
