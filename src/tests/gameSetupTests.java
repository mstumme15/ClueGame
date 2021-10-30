package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
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
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}
	
	
	@Test
	public void loadPeople() {
		ArrayList<Player> players = board.getPlayers();
		assertEquals(players.size(), 6);
		assertTrue(players.contains(new HumanPlayer("Olivia", "Blue", 17, 0)));
		assertTrue(players.contains(new ComputerPlayer("Emma", "Green", 0, 4)));
		assertTrue(players.contains(new ComputerPlayer("Oliver", "Orange", 25, 14)));
	}
	
	@Test
	public void testCardDeck() {
		ArrayList<Card> deck = board.getDeck();
		assertEquals(deck.size(), 21);
		assertTrue(deck.contains(new Card("Game Room", "ROOM")));
		assertTrue(deck.contains(new Card("Noah", "PERSON")));
		assertTrue(deck.contains(new Card("Clock", "WEAPON")));
	}
}
