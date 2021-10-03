package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {

	// Default constructor - sets up the board
	// TODO: implement
	public TestBoard() {
		super();
	}
	
	// calcTargets - calculates legal move targets given the starting cell and the path length
	// TODO: implement
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		
	}
	
	// getTargets - getter for values calculated in calcTargets
	// TODO: implement
	public Set<TestBoardCell> getTargets() {
		return new HashSet<TestBoardCell>();
	}
	
	// getCell - getter that returns the cell at location of row, column
	// TODO: implement
	public TestBoardCell getCell(int row, int column) {
		return new TestBoardCell(0, 0);
	}
}
