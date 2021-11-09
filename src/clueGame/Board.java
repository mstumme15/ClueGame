package clueGame;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;


public class Board extends JPanel {
	
	// Instance variables
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfig;
	private String setupConfigFile;
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	private static Board theInstance = new Board();
	private Set<BoardCell> targets;
	private Set<BoardCell> cellsVisited;
	
	private ArrayList<Player> players;
	private ArrayList<Card> deck;
	private Solution theAnswer;
	
	private int boardWidth;
	private int boardHeight;
	
	public static final int NUM_PLAYERS = 6;
	public static final int NUM_ROOMS = 9;
	
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
		calcAdjacencies();
		deal();
		
	}
	
	// Loads the setup file and creates the roomMap
	public void loadSetupConfig() throws BadConfigFormatException {
		try {
			File setup = new File("data/" + setupConfigFile); // Read in file
			Scanner mySetup = new Scanner(setup);
			players = new ArrayList<Player>();
			deck = new ArrayList<Card>();
			
			while (mySetup.hasNextLine()) {
				String[] line = mySetup.nextLine().split(",");
				
				if (line[0].equals("Room") || line[0].equals("Space")) { // Sets up rooms
					Room room = new Room();
					String roomName = line[1].substring(1);
					room.setName(roomName);
					char character = line[2].charAt(1);
					roomMap.put(character, room);
					if (line[0].equals("Room")) {
						deck.add(new Card(roomName, "ROOM"));
					}
					
				} 
				else if (line[0].equals("Person") && players.size()==0) { // Sets up the human player
					String name = line[1].substring(1);
					String color = line[2].substring(1);
					int row = Integer.parseInt(line[3].substring(1));
					int col = Integer.parseInt(line[4].substring(1));
					Player player = new HumanPlayer(name, color, row, col);
					players.add(player);
					deck.add(new Card(name, "PERSON"));
				}
				else if (line[0].equals("Person") && players.size()>0) { // Sets up the computer players
					String name = line[1].substring(1);
					String color = line[2].substring(1);
					int row = Integer.parseInt(line[3].substring(1));
					int col = Integer.parseInt(line[4].substring(1));
					Player player = new ComputerPlayer(name, color, row, col);
					players.add(player);
					deck.add(new Card(name, "PERSON"));
				}
				else if (line[0].equals("Weapon")) {
					String name = line[1].substring(1);
					deck.add(new Card(name, "WEAPON"));
				}
				else if (!line[0].equals("")) { // Throws exception if room type is wrong
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
					
					Room room = theInstance.getRoom(cellInitial);
					switch (initial.charAt(1)) {
					case '>':
						cell.setDoorDirection(DoorDirection.RIGHT);
						break;
					case '<':
						cell.setDoorDirection(DoorDirection.LEFT);
						break;
					case '^':
						cell.setDoorDirection(DoorDirection.UP);
						break;
					case 'v':
						cell.setDoorDirection(DoorDirection.DOWN);
						break;
					case '#':
						room.setLabelCell(cell);
						cell.setRoomLabel(true);
						break;
					case '*':
						room.setCenterCell(cell);
						cell.setRoomCenter(true);
						break;
					default:
						room.setSecretPassage(initial.charAt(1));
						cell.setSecretPassage(initial.charAt(1));
						break;
					}
					
				} else {
					cell.setDoorDirection(DoorDirection.NONE);
				}
				grid[i][j] = cell;
			}
		}
	}
	
	public void calcAdjacencies() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				
				//Checks to see if space is a walkway
				if (grid[i][j].getInitial() == 'W') {
					
					//Checks to see if space is a door
					if (grid[i][j].getDoorDirection() == DoorDirection.NONE) {
						walkway(i, j);
					} else
						doors(i, j);
					
				}
				
				//Checks to see if space is center of room
				else if(grid[i][j].isRoomCenter()) {
					Room centerRoom = getRoom(grid[i][j]);
					if (centerRoom.getSecretPassage() != '0') {
						centerRoom = getRoom(grid[i][j]);
						char secretPassage = centerRoom.getSecretPassage();
						grid[i][j].addAdj(getRoom(secretPassage).getCenterCell());
					}
				}
			}
		}
	}

	//Adds to the adjacency list for doors
	private void doors(int i, int j) {
		Room room = new Room();
		
		switch (grid[i][j].getDoorDirection()) {
		case UP:
			room = getRoom(grid[i-1][j]);
			grid[i][j].addAdj(room.getCenterCell());
			room.getCenterCell().addAdj(grid[i][j]);

			if ((i + 1) <= numRows-1  && grid[i+1][j].getInitial() == 'W') {
				grid[i][j].addAdj(grid[i+1][j]);
			}

			if ((j - 1) >= 0 && grid[i][j-1].getInitial() == 'W') {
				grid[i][j].addAdj(grid[i][j-1]);
			}

			if ((j + 1) <= numColumns-1 && grid[i][j+1].getInitial() == 'W') {
				grid[i][j].addAdj(grid[i][j+1]);
			}
			break;
			
		case DOWN:
			room = getRoom(grid[i+1][j]);
			grid[i][j].addAdj(room.getCenterCell());
			room.getCenterCell().addAdj(grid[i][j]);

			if ((i - 1) >= 0 && grid[i-1][j].getInitial() == 'W') {

				grid[i][j].addAdj(grid[i-1][j]);
			}

			if ((j - 1) >= 0 && grid[i][j-1].getInitial() == 'W') {
				grid[i][j].addAdj(grid[i][j-1]);
			}

			if ((j + 1) <= numColumns-1 && grid[i][j+1].getInitial() == 'W') {
				grid[i][j].addAdj(grid[i][j+1]);
			}
			break;
			
		case LEFT:
			room = getRoom(grid[i][j-1]);
			grid[i][j].addAdj(room.getCenterCell());
			room.getCenterCell().addAdj(grid[i][j]);

			if ((i - 1) >= 0 && grid[i-1][j].getInitial() == 'W') {
				grid[i][j].addAdj(grid[i-1][j]);
			}

			if ((i + 1) <= numRows-1  && grid[i+1][j].getInitial() == 'W') {
				grid[i][j].addAdj(grid[i+1][j]);
			}

			if ((j + 1) <= numColumns-1 && grid[i][j+1].getInitial() == 'W') {
				grid[i][j].addAdj(grid[i][j+1]);
			}
			break;
			
		case RIGHT:
			room = getRoom(grid[i][j+1]);
			grid[i][j].addAdj(room.getCenterCell());
			room.getCenterCell().addAdj(grid[i][j]);

			if ((i - 1) >= 0 && grid[i-1][j].getInitial() == 'W') {

				grid[i][j].addAdj(grid[i-1][j]);
			}

			if ((i + 1) <= numRows-1 && grid[i+1][j].getInitial() == 'W') {
				grid[i][j].addAdj(grid[i+1][j]);
			}

			if ((j - 1) >= 0 && grid[i][j-1].getInitial() == 'W') {
				grid[i][j].addAdj(grid[i][j-1]);
			}
			break;
			
		default:
			break;
		}
	}

	//Adds to the adjacency list for walkways
	private void walkway(int i, int j) {
		// Cell above
		if ((i - 1) >= 0 && grid[i-1][j].getInitial() == 'W') {
			grid[i][j].addAdj(grid[i-1][j]);
		}
		
		// Cell below
		if ((i + 1) <= numRows-1 && grid[i+1][j].getInitial() == 'W') {
			grid[i][j].addAdj(grid[i+1][j]);
		}
		
		// Cell left
		if ((j - 1) >= 0 && grid[i][j-1].getInitial() == 'W') {
			grid[i][j].addAdj(grid[i][j-1]);
		}
		
		// Cell right
		if ((j + 1) <= numColumns-1 && grid[i][j+1].getInitial() == 'W') {
			grid[i][j].addAdj(grid[i][j+1]);
		}
	}
	
	// calcTargets - calculates the possible targets on the board given the start cell and the number of moves 
	public void calcTargets(BoardCell startCell, int moves) {
		cellsVisited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		
		cellsVisited.add(startCell);
		findAllTargets(startCell, moves);
	}
	
	// findAllTargets - recursive method to find all targets
	public void findAllTargets(BoardCell currCell, int movesLeft) {
		for (BoardCell adjCell : currCell.getAdjList()) { // Loops through all adjacent cells
			if (!cellsVisited.contains(adjCell)) {
				cellsVisited.add(adjCell);
				boolean cellNotOccupied = !adjCell.getOccupied();
				
				// If adj cell is a room center or its is not occupied and its the last move
				// add adj cell to targets
				if ((movesLeft == 1 && cellNotOccupied) || adjCell.isRoomCenter()) {
					targets.add(adjCell);
				// Else call findAllTargets with adj cell and one less move
				} else if (cellNotOccupied) {
					findAllTargets(adjCell, movesLeft-1);
				}
				
				cellsVisited.remove(adjCell);
			}
		}
	}
	
	// deal - Deals the deck of cards and the solution
	public void deal() {
	
		ArrayList<Card> dealing = new ArrayList<Card>(deck); // Create a new dealing deck
		Random rand = new Random();
		Card room = dealing.get(rand.nextInt(NUM_ROOMS));
		Card person = dealing.get(rand.nextInt(NUM_PLAYERS)+NUM_ROOMS);
		Card weapon = dealing.get(rand.nextInt(dealing.size()-15)+15);
		
		// Get the answer by adding a random room, person, and weapon
		theAnswer = new Solution(room, person, weapon);
		dealing.remove(room);
		dealing.remove(person);
		dealing.remove(weapon);
		
		// Deal the rest of the cards until empty
		while(!dealing.isEmpty()) {
			for (Player player : players) {
				if(dealing.isEmpty()) {
					break;
				}
				Card card = dealing.get(rand.nextInt(dealing.size()));
				player.updateHand(card);
				dealing.remove(card);
			}
		}
		
		
	}
	
	// Checks to see if accusation is correct
	public boolean checkAccusation(Solution accusation) {
		if (theAnswer.equals(accusation)) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	// Returns the card that can disprove a suggestions
	public Card handleSuggestion(Card room, Card person, Card weapon, Player suggestingPlayer) {
		
		Card card;
		for (Player player: players) {
			if (player.equals(suggestingPlayer)) {
				continue;
			}
			else {
				card = player.disproveSuggestion(room, person, weapon);
			}
			if(card != null) {
				return card;
			}
		}
		return null;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		boardWidth = this.getWidth();
		boardHeight = this.getHeight();
		int cellWidth = boardWidth / numColumns;
		int cellHeight = boardHeight / numRows;
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				grid[i][j].draw(g, j*cellWidth, i*cellHeight, cellWidth, cellHeight);
			}
		}
		
	}
	
	
	// Getters and setters
	public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].getAdjList();
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
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
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	// For testing
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	public ArrayList<Card> getDeck() {
		return deck;
	}

	public Solution getAnswer() {
		return theAnswer;
	}
	
	public void setAnswer(Card room, Card person, Card weapon) {
		theAnswer = new Solution(room,person,weapon);
	}
}
