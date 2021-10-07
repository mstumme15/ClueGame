package clueGame;

import java.util.Map;

public class Board {
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfig;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private static Board theInstance;
	
	private Board() {
		super();
	}
	
	public static Board getInstance() {
		return new Board();
	}
	
	public void initialize() {
		
	}
	
	public void loadSetupConfig() {
		
	}
	
	public void loadLayoutConfig() {
		
	}
	
	public void setConfigFiles(String layout, String setup) {
		layoutConfig = layout;
		setupConfigFile = setup;
	}
	
	public Room getRoom(char room) {
		return new Room();
		//return roomMap.get(room);
	}
	
	public Room getRoom(BoardCell cell) {
		return new Room();
		//return roomMap.get(cell.getInitial());
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}
	
	public BoardCell getCell(int row, int col) {
		return new BoardCell();
		//return grid[row][col];
	}
}
