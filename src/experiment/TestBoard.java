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
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		visited = new HashSet<TestBoardCell>();
		targets = new HashSet<TestBoardCell>();
		
		visited.add(startCell);
		findTargets(startCell, pathLength);
		
	}
	
	// findTargets - recursive algorithm that finds all the targets
	public void findTargets(TestBoardCell thisCell, int numSteps) {
		
		for (TestBoardCell adjCell : thisCell.getAdjList()) {
			if(visited.contains(adjCell)) {
				continue;
			}
			else if(adjCell.getOccupied()){
				continue;
			}
			else if(adjCell.isRoom()) {
				targets.add(adjCell);
				visited.remove(adjCell);
			}
			else {
				visited.add(adjCell);
				if(numSteps == 1) {
					targets.add(adjCell);
				}
				else {
					findTargets(adjCell, numSteps-1);
					}
				
				visited.remove(adjCell);
			}
		}
		
	}
	
	// getTargets - getter for values calculated in calcTargets
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
	// getCell - getter that returns the cell at location of row, column
	public TestBoardCell getCell(int row, int column) {
		return grid[row][column];
	}
}
