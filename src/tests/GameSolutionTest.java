package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
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

class GameSolutionTest {
	
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	private static Card oliviaCard, emmaCard, ameliaCard, liamCard, noahCard, oliverCard, bedroomCard, kitchenCard, 
	officeCard, theaterCard, livingRoomCard, laundryRoomCard, gameRoomCard, sunRoomCard, greenRoomCard, pistolCard,
	ropeCard, daggerCard, hammerCard, clockCard, bottleCard;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		
		oliviaCard = new Card("Olivia", "PERSON");
		emmaCard = new Card("Emma", "PERSON");
		ameliaCard = new Card("Amelia", "PERSON");
		liamCard = new Card("Liam", "PERSON");
		noahCard = new Card("Noah", "PERSON");
		oliverCard = new Card("Oliver", "PERSON");
		
		bedroomCard = new Card("Bedroom", "ROOM");
		kitchenCard = new Card("Kitchen", "ROOM");
		officeCard = new Card("Office", "ROOM");
		theaterCard = new Card("Theater Room", "ROOM");
		livingRoomCard = new Card("Living Room", "ROOM");
		gameRoomCard = new Card("Game Room", "ROOM");
		sunRoomCard = new Card("Sun Room", "ROOM");
		greenRoomCard = new Card("Green Room", "ROOM");
		laundryRoomCard = new Card("Laundry Room", "ROOM");
		
		pistolCard = new Card("Pistol", "WEAPON");
		ropeCard = new Card("Rope", "WEAPON");
		daggerCard = new Card("Dagger", "WEAPON");
		hammerCard = new Card("Hammer", "WEAPON");
		clockCard = new Card("Clock", "WEAPON");
		bottleCard = new Card("Bottle", "WEAPON");
		
		
	}

	@Test
	void checkAccusation() {
		board.setAnswer(gameRoomCard, oliviaCard, pistolCard);
		assertTrue(board.checkAccusation(new Solution(gameRoomCard, oliviaCard, pistolCard)));
		
		assertFalse(board.checkAccusation(new Solution(greenRoomCard, oliviaCard, pistolCard)));
		assertFalse(board.checkAccusation(new Solution(gameRoomCard, emmaCard, pistolCard)));
		assertFalse(board.checkAccusation(new Solution(gameRoomCard, oliviaCard, ropeCard)));
		
	}
	
	//@Test
	void disproveSuggestion() {
		fail("Not yet implemented");
	}
	
	//@Test
	void handleSuggestion() {
		fail("Not yet implemented");
	}

}
