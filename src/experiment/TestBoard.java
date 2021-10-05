package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	final static int COLS = 4;
	final static int ROWS = 4;
	
	// Default constructor - sets up the board
	public TestBoard() {
		super();
		grid = new TestBoardCell[ROWS][COLS];
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				grid[i][j] = new TestBoardCell(i, j);
			}
		}
		
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if ((i - 1) >= 0) {
					grid[i][j].addAdjacency(grid[i-1][j]);
				}
				
				if ((i + 1) <= 3) {
					grid[i][j].addAdjacency(grid[i+1][j]);
				}
				
				if ((j - 1) >= 0) {
					grid[i][j].addAdjacency(grid[i][j-1]);
				}
				
				if ((j + 1) <= 3) {
					grid[i][j].addAdjacency(grid[i][j+1]);
				}
			}
		}
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
		return grid[row][column];
	}
}
