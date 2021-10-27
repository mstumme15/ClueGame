package clueGame;

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
}
