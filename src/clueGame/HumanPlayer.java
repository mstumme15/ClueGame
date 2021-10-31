package clueGame;

import java.util.ArrayList;

public class HumanPlayer extends Player {
	
	public HumanPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}

	@Override
	public ArrayList<Card> getHand() {
		return hand;
	}

	

	

	
	
}
