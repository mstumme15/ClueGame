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
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

class ComputerAITest {
	
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
	void createSuggestion() {
		ComputerPlayer emily = new ComputerPlayer("Emily", "Red", 2, 1);
		
		//Add cards to computer player hand
		emily.updateHand(emmaCard);
		emily.updateHand(clockCard);
		emily.updateHand(bottleCard);
		
		//Add the cards in the hand to the seen cards
		emily.updateSeen(emmaCard);
		emily.updateSeen(clockCard);
		emily.updateSeen(bottleCard);
		
		//Add players and weapons to seen
		emily.updateSeen(ameliaCard);
		emily.updateSeen(liamCard);
		emily.updateSeen(oliviaCard);
		emily.updateSeen(hammerCard);
		emily.updateSeen(daggerCard);
		
		//Generate the suggestion
		Solution suggestion = emily.createSuggestion(board);
		
		// Make sure the room is the correct room
		assertEquals(suggestion.getRoom().getName(), "Bedroom");
		
		//Tests for multiple weapons and persons not seen
		int pistolCount = 0;
		int ropeCount = 0;
		int noahCount = 0;
		int oliverCount = 0;
		int otherCount = 0;
		
		for (int i= 0; i < 20; i++) {
			suggestion = emily.createSuggestion(board);
			// Checks that weapons are valid
			if (suggestion.getWeapon().getName().equals("Pistol")) {
				pistolCount++;
			}
			else if (suggestion.getWeapon().getName().equals("Rope")) {
				ropeCount++;
			}
			else {
				otherCount++;
			}
			
			// Check that players are valid
			if (suggestion.getPerson().getName().equals("Noah")) {
				noahCount++;
			}
			else if (suggestion.getPerson().getName().equals("Oliver")) {
				oliverCount++;
			}
			else {
				otherCount++;
			}
		}
		
		// Test to make sure only valid ones are picked
		assertTrue(pistolCount > 1);
		assertTrue(ropeCount > 1);
		assertTrue(noahCount > 1);
		assertTrue(oliverCount > 1);
		assertEquals(otherCount, 0);
		
		// Test for only one weapon and person not seen
		emily.updateSeen(pistolCard);
		emily.updateSeen(noahCard);
		
		suggestion = emily.createSuggestion(board);
		
		assertEquals(suggestion, new Solution(bedroomCard,oliverCard,ropeCard));
	}

	
	@Test
	public void selectTarget() { 
		ComputerPlayer joel = new ComputerPlayer("Joel", "Green", 6, 9);
		BoardCell cellNearOffice = board.getCell(joel.getRow(), joel.getCol());
		BoardCell target;
		
		// Office is not in seen list
		board.calcTargets(cellNearOffice, 2);
		target = joel.selectTarget(board.getTargets());
		assertTrue(target.isRoomCenter());
		assertTrue(target.getInitial() == 'O');
		
		// Office is in seen list
		joel.updateSeen(officeCard);
		board.calcTargets(cellNearOffice, 2);
		int countR6C7 = 0;
		int countR7C8 = 0;
		int countR8C9 = 0;
		int countR7C10 = 0;
		int countR6C11 = 0;
		int countR5C10 = 0;
		for (int i = 0; i < 60; i++) {
			target = joel.selectTarget(board.getTargets());
			if (target.equals(new BoardCell(6, 7, 'W'))) {
				countR6C7 += 0;
			}
			else if (target.equals(new BoardCell(7, 8, 'W'))) {
				countR7C8 += 0;
			}
			else if (target.equals(new BoardCell(8, 9, 'W'))) {
				countR8C9 += 0;
			}
			else if (target.equals(new BoardCell(7, 10, 'W'))) {
				countR7C10 += 0;
			}
			else if (target.equals(new BoardCell(6, 11, 'W'))) {
				countR6C11 += 0;
			}
			else if (target.equals(new BoardCell(5, 10, 'W'))) {
				countR5C10 += 0;
			}
		}
		
		assertTrue(countR6C7 > 0);
		assertTrue(countR7C8 > 0);
		assertTrue(countR8C9 > 0);
		assertTrue(countR7C10 > 0);
		assertTrue(countR6C11 > 0);
		assertTrue(countR5C10 > 0);
		
		
		// Random target not by a room
		joel.setRow(17);
		joel.setCol(16);
		BoardCell cellNoRooms = board.getCell(joel.getRow(), joel.getCol());
		board.calcTargets(cellNoRooms, 1);
		int countR17C15 = 0;
		int countR16C16 = 0;
		int countR17C17 = 0;
		int countR18C16 = 0;
		for (int i = 0; i < 40; i++) {
			target = joel.selectTarget(board.getTargets());
			if (target.getRow() == 17) {
				if (target.getCol() == 15) {
					countR17C15 += 1;
				}
				else {
					countR17C17 += 1;
				}
			}
			else if (target.getCol() == 16) {
				if (target.getRow() == 16) {
					countR16C16 += 1;
				}
				else { 
					countR18C16 += 1;
				}
			}
		}
		assertTrue(countR17C15 > 0);
		assertTrue(countR16C16 > 0);
		assertTrue(countR17C17 > 0);
		assertTrue(countR18C16 > 0);
	}
}
