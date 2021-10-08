package clueGame;

import java.util.Set;

public class BoardCell {
	
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection = DoorDirection.NONE;
	private boolean roomLabel = false;
	private boolean roomCenter = false;
	private char secretPassage;
	private Set<BoardCell> adjList;
	

	public BoardCell() {
		super();
	}
	
	public BoardCell(int row, int col, char initial) {
		super();
		this.row = row;
		this.col = col;
		this.initial = initial;
	}
	
	public void addAdj(BoardCell adj) {
		
	}
	
	public boolean isDoorway() {
		if (doorDirection == DoorDirection.NONE) {
			return false;
		}
		else{
			return true;
		}
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
}
