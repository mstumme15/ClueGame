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
		BoardCell cellNearOffice = board.getCell(joel.getRow(), joel.getColumn());
		BoardCell target;
		
		// Office is not in seen list
		board.calcTargets(cellNearOffice, 3);
		target = joel.selectTarget(board.getTargets(), board);
		System.out.println(target.getRow());
		System.out.println(target.getCol());
		assertTrue(target.isRoomCenter());
		assertTrue(target.getInitial() == 'O');
		
		// Office is in seen list
		joel.updateSeen(officeCard);
		board.calcTargets(cellNearOffice, 3);
		int countR8C8 = 0;
		int countR6C12 = 0;
		int countR5C9 = 0;
		int countOffice = 0;
		for (int i = 0; i < 140; i++) { // 10 loop iterations per possible target
			target = joel.selectTarget(board.getTargets(), board);
			
			countR8C8 = compareTargetToExpected(target, 8, 8, 'W', countR8C8);
			countR6C12 = compareTargetToExpected(target, 6, 12, 'W', countR6C12);
			countR5C9 = compareTargetToExpected(target, 5, 9, 'W', countR5C9);
			countOffice = compareTargetToExpected(target, 2, 8, 'O', countOffice);
		}
		
		assertTrue(countR8C8 > 0);
		assertTrue(countR6C12 > 0);
		assertTrue(countR5C9 > 0);
		assertTrue(countOffice > 0);
		
		
		// Random target not by a room
		joel.setRow(17);
		joel.setCol(16); // set location away from rooms
		BoardCell cellNoRooms = board.getCell(joel.getRow(), joel.getColumn());
		board.calcTargets(cellNoRooms, 1);
		int countR17C15 = 0;
		int countR16C16 = 0;
		int countR17C17 = 0;
		int countR18C16 = 0;
		for (int i = 0; i < 40; i++) { // 10 loop iterations per possible target
			target = joel.selectTarget(board.getTargets(), board);
			
			countR17C15 = compareTargetToExpected(target, 17, 15, 'W', countR17C15);
			countR16C16 = compareTargetToExpected(target, 16, 16, 'W', countR16C16);
			countR17C17 = compareTargetToExpected(target, 17, 17, 'W', countR17C17);
			countR18C16 = compareTargetToExpected(target, 18, 16, 'W', countR18C16);
		}
		assertTrue(countR17C15 > 0);
		assertTrue(countR16C16 > 0);
		assertTrue(countR17C17 > 0);
		assertTrue(countR18C16 > 0);
	}
	
	public int compareTargetToExpected(BoardCell target, int row, int col, char initial, int countExpected) {
		if (target.equals(new BoardCell(row, col, initial))) {
			return ++countExpected;
		}
		else {
			return countExpected;
		}
	}
}
