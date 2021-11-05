package clueGame;

import java.util.ArrayList;

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
		return new Solution(new Card("test", "ROOM"), new Card("test", "PERSON"), new Card("test", "WEAPON"));
	}
}
