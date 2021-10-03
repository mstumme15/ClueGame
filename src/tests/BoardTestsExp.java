package tests;

import static org.junit.jupiter.api.Assertions.*;

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

}
