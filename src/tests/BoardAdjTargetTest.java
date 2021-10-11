package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	 
		private static Board board;
		
		@BeforeAll
		public static void setUp() {
			// Board is singleton, get the only instance
			board = Board.getInstance();
			// set the file names to use my config files
			board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
			// Initialize will load config files 
			board.initialize();
		}
		
		
		//Tests the adjacency of walkways 
		@Test
		public void testAdjacencyWalkways()
		{
			// Test on bottom edge of board 
			Set<BoardCell> testList = board.getAdjList(25, 6);
			assertEquals(2, testList.size());
			assertTrue(testList.contains(board.getCell(25, 5)));
			assertTrue(testList.contains(board.getCell(24, 6)));
			
			// Test board on left edge and next to a room no doorways
			testList = board.getAdjList(6, 0);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCell(6, 1)));
			
			// Test top edge of board
			testList = board.getAdjList(0, 12);
			assertEquals(2, testList.size());
			assertTrue(testList.contains(board.getCell(0, 11)));
			assertTrue(testList.contains(board.getCell(1, 12)));

			// Test top edge of board
			testList = board.getAdjList(17, 24);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCell(17, 23)));
			
			// Test walkway surrounded by walkways
			testList = board.getAdjList(11, 7);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(11, 6)));
			assertTrue(testList.contains(board.getCell(11, 8)));
			assertTrue(testList.contains(board.getCell(10, 7)));
			assertTrue(testList.contains(board.getCell(12, 7)));
			
		}
		
		
		//Tests the adjacency of doors
		@Test
		public void testAdjacencyDoors()
		{
			// Test adjacency of door ways going up
			Set<BoardCell> testList = board.getAdjList(13, 5);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(13, 6)));
			assertTrue(testList.contains(board.getCell(14, 5)));
			assertTrue(testList.contains(board.getCell(11, 2)));
			
			// Test adjacency of door ways going left
			testList = board.getAdjList(4, 3);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(5, 3)));
			assertTrue(testList.contains(board.getCell(4, 4)));
			assertTrue(testList.contains(board.getCell(2, 1)));
			
			// Test adjacency of door ways going right
			testList = board.getAdjList(16, 20);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(16, 19)));
			assertTrue(testList.contains(board.getCell(15, 20)));
			assertTrue(testList.contains(board.getCell(17, 20)));
			assertTrue(testList.contains(board.getCell(15, 22)));
			
			// Test adjacency of door ways going down
			testList = board.getAdjList(17, 8);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(17, 7)));
			assertTrue(testList.contains(board.getCell(17, 9)));
			assertTrue(testList.contains(board.getCell(16, 8)));
			assertTrue(testList.contains(board.getCell(22, 9)));
			
		}
		
		// Tests the adjacency inside rooms
		@Test
		public void testAdjacencyRooms()
		{
			// Test locations inside room not center
			Set<BoardCell> testList = board.getAdjList(3, 15);
			assertEquals(0, testList.size());
			
			testList = board.getAdjList(23, 10);
			assertEquals(3, testList.size());
			
			// Test adjacency of center of rooms
			testList = board.getAdjList(2, 8);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCell(5, 8)));
			
			testList = board.getAdjList(9, 21);
			assertEquals(2, testList.size());
			assertTrue(testList.contains(board.getCell(8, 17)));
			assertTrue(testList.contains(board.getCell(13, 19)));
			
			
			// Test adjacency of rooms with secret passage
			testList = board.getAdjList(22, 2);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(17, 4)));
			assertTrue(testList.contains(board.getCell(24, 5)));
			assertTrue(testList.contains(board.getCell(22, 21)));
		}
		
		// Tests targets out of the center of the living room for rolls 1, 2, and 3
		@Test
		public void testTargetsLivingRoom() {
			// Test roll of 1
			board.calcTargets(board.getCell(2, 18), 1);
			Set<BoardCell> targets = board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCell(5, 13)));
			assertTrue(targets.contains(board.getCell(5, 14)));
			assertTrue(targets.contains(board.getCell(5, 15)));
			assertTrue(targets.contains(board.getCell(22, 2))); // Test secret passage into green room
			
			// Test roll of 2
			board.calcTargets(board.getCell(2, 18), 2);
			targets = board.getTargets();
			assertEquals(9, targets.size());
			assertTrue(targets.contains(board.getCell(5, 14)));
			assertTrue(targets.contains(board.getCell(5, 12)));
			assertTrue(targets.contains(board.getCell(6, 15)));
			assertTrue(targets.contains(board.getCell(22, 2)));
			
			// Test roll of 3
			board.calcTargets(board.getCell(2, 18), 3);
			targets = board.getTargets();
			assertEquals(14, targets.size());
			assertTrue(targets.contains(board.getCell(5, 15)));
			assertTrue(targets.contains(board.getCell(6, 12)));
			assertTrue(targets.contains(board.getCell(6, 14)));
			assertTrue(targets.contains(board.getCell(7, 15)));
			assertTrue(targets.contains(board.getCell(22, 2)));
		}
		
		// Tests targets out of the center of the sun room for rolls 1, 2, and 3
		@Test
		public void testTargetsSunRoom() {
			// Test roll of 1
			board.calcTargets(board.getCell(22, 9), 1);
			Set<BoardCell> targets = board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(17, 8)));
			assertTrue(targets.contains(board.getCell(23, 14)));
			
			// Test roll of 2
			board.calcTargets(board.getCell(22, 9), 2);
			targets = board.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCell(17, 7)));
			assertTrue(targets.contains(board.getCell(16, 8)));
			assertTrue(targets.contains(board.getCell(23, 15)));
			assertTrue(targets.contains(board.getCell(24, 14)));

			// Test roll of 3
			board.calcTargets(board.getCell(22, 9), 3);
			targets = board.getTargets();
			assertEquals(9, targets.size());
			assertTrue(targets.contains(board.getCell(25, 14)));
			assertTrue(targets.contains(board.getCell(24, 16)));
			assertTrue(targets.contains(board.getCell(17, 10)));
			assertTrue(targets.contains(board.getCell(16, 7)));
			assertTrue(targets.contains(board.getCell(15, 8)));
		}
		
		// Tests targets when in walkway for rolls 1, 2, and 3
		@Test
		public void testTargetsWalkway1() {
			// Test roll of 1
			board.calcTargets(board.getCell(9, 9), 1);
			Set<BoardCell> targets = board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(9, 8)));
			assertTrue(targets.contains(board.getCell(8, 9)));
			assertTrue(targets.contains(board.getCell(10, 9)));

			// Test roll of 2
			board.calcTargets(board.getCell(9, 9), 2);
			targets = board.getTargets();
			assertEquals(5, targets.size());
			assertTrue(targets.contains(board.getCell(8, 8)));
			assertTrue(targets.contains(board.getCell(10, 8)));
			assertTrue(targets.contains(board.getCell(9, 7)));
			assertTrue(targets.contains(board.getCell(7, 9)));

			// Test roll of 3
			board.calcTargets(board.getCell(9, 9), 3);
			targets = board.getTargets();
			assertEquals(11, targets.size());
			assertTrue(targets.contains(board.getCell(7, 10)));
			assertTrue(targets.contains(board.getCell(9, 6)));
			assertTrue(targets.contains(board.getCell(10, 9)));
			assertTrue(targets.contains(board.getCell(9, 8)));
			assertTrue(targets.contains(board.getCell(7, 8)));
		}
		
		// Tests targets when in walkway for rolls 1, 2, and 3
		@Test
		public void testTargetsWalkway2() {
			// Test roll of 1
			board.calcTargets(board.getCell(15, 5), 1);
			Set<BoardCell> targets = board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCell(15, 4)));
			assertTrue(targets.contains(board.getCell(15, 6)));
			assertTrue(targets.contains(board.getCell(14, 5)));
			assertTrue(targets.contains(board.getCell(16, 5)));

			// Test roll of 2
			board.calcTargets(board.getCell(15, 5), 2);
			targets = board.getTargets();
			assertEquals(7, targets.size());
			assertTrue(targets.contains(board.getCell(13, 5)));
			assertTrue(targets.contains(board.getCell(15, 7)));
			assertTrue(targets.contains(board.getCell(16, 6)));
			assertTrue(targets.contains(board.getCell(15, 4)));

			// Test roll of 3
			board.calcTargets(board.getCell(15, 5), 3);
			targets = board.getTargets();
			assertEquals(12, targets.size());
			assertTrue(targets.contains(board.getCell(11, 2))); // Enter Theater
			assertTrue(targets.contains(board.getCell(17, 4)));
			assertTrue(targets.contains(board.getCell(14, 5)));
			assertTrue(targets.contains(board.getCell(17, 6)));
			assertTrue(targets.contains(board.getCell(14, 7)));
		}
		
		// Tests targets when in walkway for rolls 1, 2, and 3
		@Test
		public void testTargetsDoorway() {
			// Test roll of 1
			board.calcTargets(board.getCell(17, 23), 1);
			Set<BoardCell> targets = board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(22, 21)));
			assertTrue(targets.contains(board.getCell(17, 24)));
			assertTrue(targets.contains(board.getCell(17, 22)));

			// Test roll of 2
			board.calcTargets(board.getCell(17, 23), 2);
			targets = board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(22, 21)));
			assertTrue(targets.contains(board.getCell(17, 21)));
			
			// Test roll of 3
			board.calcTargets(board.getCell(17, 23), 3);
			targets = board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(22, 21)));
			assertTrue(targets.contains(board.getCell(17, 20)));
		}
		
		@Test
		public void testOccupied() {
			// Walkway blocked by two players
			board.getCell(9, 8).setOccupied(true);
			board.getCell(10, 9).setOccupied(true);
			board.calcTargets(board.getCell(10, 8), 4);
			board.getCell(9, 8).setOccupied(false);
			board.getCell(10, 9).setOccupied(false);
			Set<BoardCell> targets = board.getTargets();
			assertEquals(16, targets.size());
			assertTrue(targets.contains(board.getCell(11, 2)));
			assertTrue(targets.contains(board.getCell(8, 8)));
			assertTrue(targets.contains(board.getCell(8, 6)));
			assertTrue(targets.contains(board.getCell(14, 8)));
			// Places that couldn't be moved to because of the block
			assertFalse(targets.contains(board.getCell(6, 8)));
			assertFalse(targets.contains(board.getCell(9, 9)));
			assertFalse(targets.contains(board.getCell(7, 9)));
			
			// Can still get in occupied room
			board.getCell(2, 1).setOccupied(true);
			board.calcTargets(board.getCell(4, 4), 2);
			board.getCell(2, 1).setOccupied(false);
			targets = board.getTargets();
			assertEquals(7, targets.size());
			assertTrue(targets.contains(board.getCell(2, 1)));
			assertTrue(targets.contains(board.getCell(4, 6)));
			assertTrue(targets.contains(board.getCell(5, 3)));
			
			// Blocked doorway
			board.getCell(13, 5).setOccupied(true);
			board.calcTargets(board.getCell(11, 2), 4);
			board.getCell(13, 5).setOccupied(false);
			targets = board.getTargets();
			assertEquals(9, targets.size());
			assertTrue(targets.contains(board.getCell(15, 4)));
			assertTrue(targets.contains(board.getCell(11, 6)));
			assertTrue(targets.contains(board.getCell(13, 9)));
			assertTrue(targets.contains(board.getCell(14, 5)));
		}
}
