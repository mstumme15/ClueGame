package clueGame;

import java.util.ArrayList;
import java.util.Random;


public class ComputerPlayer extends Player {
	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}
	
	@Override
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	// Creates a suggestion based off room and seen cards
	public Solution createSuggestion(Board board) {
		ArrayList<Card> deck = board.getDeck();
		BoardCell cell = board.getCell(row,column);
		Room room = board.getRoom(cell);
		String roomName = room.getName();
		
		// Create the correct room
		Card suggestedRoom = new Card(roomName, "ROOM");
		
		Random rand = new Random();
		
		
		Card suggestedPerson = deck.get(rand.nextInt(Board.NUM_PLAYERS)+Board.NUM_ROOMS);
		while(seen.contains(suggestedPerson)) {
			suggestedPerson = deck.get(rand.nextInt(Board.NUM_PLAYERS)+Board.NUM_ROOMS);
		}
		
		Card suggestedWeapon = deck.get(rand.nextInt(deck.size()-15)+15);
		while(seen.contains(suggestedWeapon)) {
			suggestedWeapon = deck.get(rand.nextInt(deck.size()-15)+15);
		}

		return new Solution(suggestedRoom, suggestedPerson, suggestedWeapon);
	}
}
