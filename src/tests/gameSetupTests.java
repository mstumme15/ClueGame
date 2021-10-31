package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

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
	
	// loadPeople - tests to see if people were loaded correctly and
	// that the first person loaded in is the human player and the rest are computer players
	@Test
	public void loadPeople() {
		ArrayList<Player> players = board.getPlayers();
		assertEquals(players.size(), 6);
		assertTrue(players.contains(new HumanPlayer("Olivia", "Blue", 17, 0)));
		assertTrue(players.contains(new ComputerPlayer("Emma", "Green", 0, 4)));
		assertTrue(players.contains(new ComputerPlayer("Oliver", "Orange", 25, 14)));
	}
	
	// testCardDeck - tests to see if one of each type of card is in the deck
	// Can't test deck size without hard-coding
	@Test
	public void testCardDeck() {
		ArrayList<Card> deck = board.getDeck();
		int count = 0;
		for (Card card: deck) {
			if (card.equals(new Card("Game Room", "ROOM"))) {
				count+=1;
			}
			if (card.equals(new Card("Noah", "PERSON"))) {
				count+=1;
			}
			if (card.equals(new Card("Clock", "WEAPON"))) {
				count+=1;
			}
		}
		assertEquals(3, count);
	}
	
	@Test
	public void testCardsDealt() {
		// Tests to see if answer is valid
		Solution answer = board.getAnswer();
		assertEquals(answer.getRoom().getType(), CardType.ROOM);
		assertEquals(answer.getPerson().getType(), CardType.PERSON);
		assertEquals(answer.getWeapon().getType(), CardType.WEAPON);
		
		
		ArrayList<Player> players = board.getPlayers();
		ArrayList<Card> deck = board.getDeck();
		ArrayList<Card> dealt_deck = new ArrayList<Card>();
		int deck_size = deck.size();
		int dealt_size = 3; // For the 3 solution cards
		
	
		// Check to see if all card were dealt and no duplicates
		for (Player player : players) {
			dealt_size += player.getHand().size();
			dealt_deck.addAll(player.getHand());
		}
		
		dealt_deck.add(answer.getRoom());
		dealt_deck.add(answer.getPerson());
		dealt_deck.add(answer.getWeapon());
		
		Set<Card> unique_deck = new HashSet<Card>(dealt_deck);	
		
		assertEquals(unique_deck.size(),dealt_deck.size()); // Tests for duplicates
		assertEquals(deck_size, dealt_size); // Tests that all cards were dealt
		
	}
}
