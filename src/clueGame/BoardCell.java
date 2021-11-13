package clueGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
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
	private boolean highlight;

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
	
	// Checks if 2 cells are equal
	public boolean equals(Object o) {
		BoardCell cellToCompare = (BoardCell) o;
		if (this.row == cellToCompare.getRow() && this.col == cellToCompare.getCol()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Draws the cells based off if they are a room, walkway, or unused
	public void draw(Graphics g, int cellXLocation, int cellYLocation, int cellWidth, int cellHeight) {

		// Fills unused squares black
		if (this.getInitial() == 'X') {
			g.setColor(Color.BLACK);
			g.fillRect(cellXLocation, cellYLocation, cellWidth, cellHeight);
		}
		
		// Fills walkways yellow or highlighted blue with black border
		else if (this.getInitial() == 'W') {
			g.setColor(Color.BLACK);
			g.drawRect(cellXLocation, cellYLocation, cellWidth, cellHeight);
			if (highlight == true) {
				g.setColor(Color.CYAN);
			}
			else {
				g.setColor(Color.YELLOW);
			}
			g.fillRect(cellXLocation+1, cellYLocation+1, cellWidth-1, cellHeight-1);
			
		}
		
		// Fills rooms in gray
		else {
			if (highlight == true) {
				g.setColor(Color.CYAN);
			}
			else {
				g.setColor(Color.GRAY);
			}
			g.fillRect(cellXLocation, cellYLocation, cellWidth, cellHeight);
		}

	}
	
	// Draws the doors based off of the direction of the door
	public void drawDoors(Graphics g, int cellXLocation, int cellYLocation, int cellWidth, int cellHeight) {
		
		g.setColor(Color.BLUE);
		((Graphics2D) g).setStroke(new BasicStroke(5)); // Set the stroke to be thicker for the doors
		
		switch(doorDirection) {
		case UP:
			g.drawLine(cellXLocation+2, cellYLocation-1, cellXLocation - 2 + cellWidth, cellYLocation -1);
			break;
		case DOWN:
			g.drawLine(cellXLocation+2, cellYLocation+cellHeight +1, cellXLocation - 2 + cellWidth, cellYLocation+cellHeight +1);
			break;
		case LEFT:
			g.drawLine(cellXLocation-1, cellYLocation+2, cellXLocation -1, cellYLocation +cellHeight -2);
			break;
		case RIGHT:
			g.drawLine(cellXLocation + cellWidth+1, cellYLocation+2, cellXLocation + cellWidth+1, cellYLocation +cellHeight -2);
			break;
		}
	
		
	}
	// Draws the names of the rooms
	public void drawNames(Graphics g, int cellXLocation, int cellYLocation, String name) {
		g.setColor(Color.BLUE);
		g.setFont(new Font("Serif", Font.ITALIC, 16));
		g.drawString(name, cellXLocation, cellYLocation);
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
	
	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}
}
