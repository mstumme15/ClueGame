package clueGame;

import java.util.ArrayList;

public class ComputerPlayer extends Player {
	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}
	
	@Override
	public ArrayList<Card> getHand() {
		return new ArrayList<Card>();
	}
}
