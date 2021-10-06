package tests;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

class BoardTestsExp {
	TestBoard board;
	
	@BeforeEach
	public void setUp() throws Exception {
		board = new TestBoard();
	}

	// testAdjacency - used to test the adjacency lists of various cells on the board
	@Test
	public void testAdjacency() {
		// Tests adjacency list of top left corner cell
		TestBoardCell topLeft = board.getCell(0, 0); // Set up cell
		Set<TestBoardCell> testListTopLeft = topLeft.getAdjList(); // Get adjacency list
		// Compare list to expected cells
		Assert.assertTrue(testListTopLeft.contains(board.getCell(1, 0)));
		Assert.assertTrue(testListTopLeft.contains(board.getCell(0, 1)));
		
		// Tests adjacency list of bottom right corner cell
		TestBoardCell bottomRight = board.getCell(3, 3);
		Set<TestBoardCell> testListBottomRight = bottomRight.getAdjList();
		Assert.assertTrue(testListBottomRight.contains(board.getCell(3, 2)));
		Assert.assertTrue(testListBottomRight.contains(board.getCell(2, 3)));
		
		// Tests adjacency list of right edge cell
		TestBoardCell rightEdge = board.getCell(1, 3);
		Set<TestBoardCell> testListRightEdge = rightEdge.getAdjList();
		Assert.assertTrue(testListRightEdge.contains(board.getCell(0, 3)));
		Assert.assertTrue(testListRightEdge.contains(board.getCell(1, 2)));
		Assert.assertTrue(testListRightEdge.contains(board.getCell(2, 3)));
		
		// Tests adjacency list of left edge cell
		TestBoardCell leftEdge = board.getCell(1, 0);
		Set<TestBoardCell> testListLeftEdge = leftEdge.getAdjList();
		Assert.assertTrue(testListLeftEdge.contains(board.getCell(0, 0)));
		Assert.assertTrue(testListLeftEdge.contains(board.getCell(1, 1)));
		Assert.assertTrue(testListLeftEdge.contains(board.getCell(2, 0)));
		
		// Tests adjacency list a cell in the middle of the grid
		TestBoardCell middleGrid = board.getCell(2, 2);
		Set<TestBoardCell> testListMiddleGrid = middleGrid.getAdjList();
		Assert.assertTrue(testListMiddleGrid.contains(board.getCell(1, 2)));
		Assert.assertTrue(testListMiddleGrid.contains(board.getCell(2, 1)));
		Assert.assertTrue(testListMiddleGrid.contains(board.getCell(2, 3)));
		Assert.assertTrue(testListMiddleGrid.contains(board.getCell(3, 2)));
	}
	
	// testTargetCreation - used to test targets on an empty board
	@Test
	public void testTargetEmpty() {
		
		//Test an empty board
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell,3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		
	}
	
	// testTargetOccupied - used to test targets on occupied board
	@Test
	public void testTargetOccupied() {
		
		// Tests if targets is set correctly with occupied board
		board.getCell(0,1).setOccupied(true);
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell,2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
	
	}
	
	// testTargetRoom - used to test targets on board with room in it
	@Test
	public void testTargetRoom() {
		
		// Tests if targets is set correctly with a board with a room in it
		board.getCell(1,1).setRoom(true);
		TestBoardCell cell = board.getCell(2, 1);
		board.calcTargets(cell,3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	
	}
	
	// testTargetMixed1- used to test targets on board with room and occupied
	@Test
	public void testTargetMixed1() {
		
		// Tests if targets is set correctly with occupied board and a room
		board.getCell(0,2).setOccupied(true);
		board.getCell(1,2).setRoom(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell,4);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));
	
	}
		
	// testTargetMixed2- used to test targets on board with room and occupied
	@Test
	public void testTargetMixed2() {
		
		// Tests if targets is set correctly with occupied board and a room
		board.getCell(1,1).setOccupied(true);
		board.getCell(2,2).setRoom(true);
		TestBoardCell cell = board.getCell(2, 1);
		board.calcTargets(cell,5);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(5, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 0)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	
	}
	
	

}
