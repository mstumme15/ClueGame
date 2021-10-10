package tests;

import static org.junit.Assert.assertEquals;
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
}
