package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class gameSetupTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");		
		// Initialize will load config files 
		board.initialize();
	}
	
	
	@Test
	public void loadPeople() {
		assertEquals(board.getPlayers().size(), 6);
		assertTrue(board.getPlayers().contains(new HumanPlayer("Olivia", "Blue", 17, 0)));
		assertTrue(board.getPlayers().contains(new ComputerPlayer("Emma", "Green", 0, 4)));
		assertTrue(board.getPlayers().contains(new ComputerPlayer("Oliver", "Orange", 25, 14)));
	}
}
