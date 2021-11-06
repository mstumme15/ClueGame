package clueGame;

import java.util.ArrayList;
import java.util.Random;

public abstract class Player{

	private String name;
	private String color;
	protected int row;
	protected int column;
	protected ArrayList<Card> hand;
	protected ArrayList<Card> seen;
	
	public Player(String name, String color, int row, int column) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
		hand = new ArrayList<Card>();
		seen = new ArrayList<Card>();
	}

	//Overide the equals method in order to see if list of players contains certain values
	@Override 
	public boolean equals(Object p){
		Player o = (Player) p;
        if (this.name.equals(o.name) && this.color.equals(o.color) && this.row == o.row && this.column == o.column) {
            return true;
        }
        else {
            return false;
        }
    }
	
	public abstract ArrayList<Card> getHand();
	
	// Updates the hand
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	// Updates the seen cards
	public void updateSeen(Card card) {
		seen.add(card);
	}
	
	// Disproves a player suggestion or returns null
	public Card disproveSuggestion(Card room, Card person, Card weapon) {
		ArrayList<Card> match = new ArrayList<Card>();
		for (Card card : hand) {
			if (card.equals(room) || card.equals(person) || card.equals(weapon)) {
				match.add(card);
			}
		}
		
		int numMatches = match.size();
		
		if (numMatches == 0) {
			return null;
		}
		else if (numMatches == 1) {
			return match.get(0);
		}
		else {
			Random rand = new Random();
			int idx = rand.nextInt(numMatches);
			return match.get(idx);
		}
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int column) {
		this.column = column;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Card> getSeen() {
		return seen;
	}
}
