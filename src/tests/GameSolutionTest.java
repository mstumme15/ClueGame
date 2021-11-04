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
		
		// Setup player cards
		oliviaCard = new Card("Olivia", "PERSON");
		emmaCard = new Card("Emma", "PERSON");
		ameliaCard = new Card("Amelia", "PERSON");
		liamCard = new Card("Liam", "PERSON");
		noahCard = new Card("Noah", "PERSON");
		oliverCard = new Card("Oliver", "PERSON");
		
		// Setup room cards
		bedroomCard = new Card("Bedroom", "ROOM");
		kitchenCard = new Card("Kitchen", "ROOM");
		officeCard = new Card("Office", "ROOM");
		theaterCard = new Card("Theater Room", "ROOM");
		livingRoomCard = new Card("Living Room", "ROOM");
		gameRoomCard = new Card("Game Room", "ROOM");
		sunRoomCard = new Card("Sun Room", "ROOM");
		greenRoomCard = new Card("Green Room", "ROOM");
		laundryRoomCard = new Card("Laundry Room", "ROOM");
		
		// Setup weapon cards
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
		
		//Testing to make sure wrong solutions arent correct
		assertFalse(board.checkAccusation(new Solution(greenRoomCard, oliviaCard, pistolCard)));
		assertFalse(board.checkAccusation(new Solution(gameRoomCard, emmaCard, pistolCard)));
		assertFalse(board.checkAccusation(new Solution(gameRoomCard, oliviaCard, ropeCard)));
		
	}
	
	@Test
	void disproveSuggestion() {
		Player testPlayer = new HumanPlayer("Mike", "Blue", 17, 0);
		testPlayer.updateHand(bedroomCard);
		testPlayer.updateHand(pistolCard);
		testPlayer.updateHand(laundryRoomCard);
		
		//Testing one matching card
		assertEquals(bedroomCard, testPlayer.disproveSuggestion(bedroomCard, oliverCard, ropeCard));
		
		
		//Testing multiple matching
		int bedroomCount = 0;
		int pistolCount = 0;
		Card match;
		for (int i = 0; i < 10; i++) {
			match = testPlayer.disproveSuggestion(bedroomCard, oliverCard, pistolCard);
			if (match.equals(bedroomCard)) {
				bedroomCount += 1;
			}
			else if(match.equals(pistolCard)){
				pistolCount += 1;
			}
		}
		
		assertTrue(bedroomCount >= 1);
		assertTrue(pistolCount >= 1);
		
		
		//Testing with no matching
		assertEquals(null, testPlayer.disproveSuggestion(sunRoomCard, oliverCard, ropeCard));
	}
	
	@Test
	void handleSuggestion() {
		ArrayList<Player> players = new ArrayList<Player>();
		Player mike = new HumanPlayer("Mike", "Blue", 17, 0);
		Player emily = new ComputerPlayer("Emily", "Red", 3, 0);
		Player joel = new ComputerPlayer("Joel", "Green", 20, 0);
		players.add(mike);
		players.add(emily);
		players.add(joel);
		
		mike.updateHand(bedroomCard);
		mike.updateHand(pistolCard);
		mike.updateHand(laundryRoomCard);
		
		emily.updateHand(emmaCard);
		emily.updateHand(clockCard);
		emily.updateHand(bottleCard);
		
		joel.updateHand(theaterCard);
		joel.updateHand(gameRoomCard);
		joel.updateHand(daggerCard);
		
		board.setPlayers(players);
		
		//Testing no matches
		Card answer = board.handleSuggestion(sunRoomCard, ameliaCard, ropeCard, mike);
		assertEquals(null, answer);
		
		//Testing accusing player
		answer = board.handleSuggestion(bedroomCard, ameliaCard, ropeCard, mike);
		assertEquals(null, answer);
		
		//Testing human player
		answer = board.handleSuggestion(bedroomCard, ameliaCard, pistolCard, emily);
		assertEquals(bedroomCard, answer);
		
		//Test when multiple can disprove
		answer = board.handleSuggestion(theaterCard, emmaCard, pistolCard, mike);
		assertEquals(emmaCard, answer);
		
	}

}
