package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

class FileInitTests {
	// Constants that I will use to test whether the file was loaded correctly
	
	public static final int NUM_ROWS = 26;
	public static final int NUM_COLUMNS = 25;
	public static final int NUM_DOORS = 17;
	
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}
	
	@Test
	public void testRoomLabels() {
		// Tests to make sure all the rooms are loaded properly
		assertEquals("Bedroom", board.getRoom('B').getName() );
		assertEquals("Office", board.getRoom('O').getName() );
		assertEquals("Kitchen", board.getRoom('K').getName() );
		assertEquals("Living Room", board.getRoom('L').getName() );
		assertEquals("Theater Room", board.getRoom('T').getName() );
		assertEquals("Laundry Room", board.getRoom('N').getName() );
		assertEquals("Game Room", board.getRoom('A').getName() );
		assertEquals("Sun Room", board.getRoom('S').getName() );
		assertEquals("Green Room", board.getRoom('G').getName() );
		assertEquals("Walkway", board.getRoom('W').getName() );
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}
	
	// Test the four door directions and that walkways are not doors.
	@Test
	public void FourDoorDirections() {
		//Test left door
		BoardCell cell = board.getCell(4, 3);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		
		//Test up door
		cell = board.getCell(5, 8);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		
		//Test right door
		cell = board.getCell(22, 15);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		
		//Test down door
		cell = board.getCell(10, 5);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		
		// Test that walkways are not doors
		cell = board.getCell(7, 7);
		assertFalse(cell.isDoorway());
		cell = board.getCell(17, 2);
		assertFalse(cell.isDoorway());
	}
	
	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCell(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(NUM_DOORS, numDoors);
	}

}
