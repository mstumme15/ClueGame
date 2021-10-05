package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {

	private int row, col;
	private Boolean isRoom, isOccupied;
	
	private Set<TestBoardCell> adjList;
	
	// Parameterized constructor - sets up cell at location row, column
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		adjList = new HashSet<TestBoardCell>();
	}
	
	// addAdjacency - setter that adds a cell to the adjacency list
	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList(){
		return adjList;
	}
	
	public void setRoom(boolean room) {
		isRoom = room;
	}
	
	// Checks to see if the cell is in a room
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}

}
