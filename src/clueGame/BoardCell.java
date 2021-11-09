package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	
	// Instance variables
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection = DoorDirection.NONE;
	private boolean roomLabel = false;
	private boolean roomCenter = false;
	private char secretPassage;
	private Set<BoardCell> adjList;

	private boolean occupied = false;
	

	// Default constructor
	public BoardCell() {
		super();
		adjList = new HashSet<BoardCell>();
		secretPassage = '0';
	}
	
	// Parameterized constructor - sets row, cell, and initial
	public BoardCell(int row, int col, char initial) {
		super();
		this.row = row;
		this.col = col;
		this.initial = initial;
		adjList = new HashSet<BoardCell>();
		secretPassage = '0';
	}
	

	// Adds cell to instance's adjacency list
	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}
	
	public boolean equals(Object o) {
		BoardCell cellToCompare = (BoardCell) o;
		if (this.row == cellToCompare.getRow() && this.col == cellToCompare.getCol()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void draw(Graphics g, int cellXLocation, int cellYLocation, int cellWidth, int cellHeight) {
//		Rectangle currCell = new Rectangle(cellXLocation, cellYLocation, cellWidth, cellHeight);
		if (this.getInitial() == 'X') {
			g.setColor(Color.BLACK);
		}
		else if (this.getInitial() == 'W') {
			g.setColor(Color.YELLOW);
		}
		else {
			g.setColor(Color.GRAY);
		}
		
		g.fillRect(cellXLocation, cellYLocation, cellWidth, cellHeight);
		g.drawRect(cellXLocation, cellYLocation, cellWidth, cellHeight);
	}
	
	// Getters and setters
	public boolean isDoorway() {
		if (doorDirection == DoorDirection.NONE) {
			return false;
		}
		else{
			return true;
		}
	}
	
	public Set<BoardCell> getAdjList() {
		return adjList;
	}

	public char getInitial() {
		return initial;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public boolean isLabel() {
		return roomLabel;
	}
	
	public boolean isRoomCenter() {
		return roomCenter;
	}

	public char getSecretPassage() {
		return secretPassage;
	}

	public boolean isRoomLabel() {
		return roomLabel;
	}

	public void setRoomLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}

	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}

	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}
	
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	
	public boolean getOccupied() {
		return occupied;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
}
