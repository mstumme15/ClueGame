package clueGame;

import java.util.ArrayList;

public abstract class Player{

	private String name;
	private String color;
	protected int row;
	protected int column;
	protected ArrayList<Card> hand;
	
	public Player(String name, String color, int row, int column) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
		hand = new ArrayList<Card>();
	}

	//Overide the equals method in order to see if list of players contains certian values
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
	
	public void updateHand(Card card) {
		hand.add(card);
	}
}
