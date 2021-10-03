package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {

	public TestBoard() {
		super();
	}
	
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		
	}
	
	public Set<TestBoardCell> getTargets() {
		return new HashSet<TestBoardCell>();
	}
	
	public TestBoardCell getCell(int row, int column) {
		return new TestBoardCell(0, 0);
	}
}
