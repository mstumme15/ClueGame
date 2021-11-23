package clueGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public abstract class Player{

	private String name;
	private String color;
	protected int row;
	protected int column;
	protected ArrayList<Card> hand;
	protected ArrayList<Card> seen;
	private boolean inRoom;
	private boolean moved;
	private Map<String, Color> colorMap;
	
	public Player(String name, String color, int row, int column) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
		hand = new ArrayList<Card>();
		seen = new ArrayList<Card>();
		colorMap = new HashMap<String, Color>();
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
		if (!seen.contains(card)) {
			seen.add(card);
		}
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
	
	// Draws the players on the board based off their location
	public void draw(Graphics g, int cellWidth, int cellHeight, int offset) {
		// Add colors to color map
		colorMap.put("Blue", Color.BLUE);
		colorMap.put("Green", Color.GREEN);
		colorMap.put("Red", Color.RED);
		colorMap.put("White", Color.WHITE);
		colorMap.put("Pink", Color.PINK);
		colorMap.put("Orange", Color.ORANGE);
		
		((Graphics2D) g).setStroke(new BasicStroke(2)); // Changes the stroke width of circle
		
		// Draw the circle and fill with appropriate color
		g.setColor(Color.BLACK);
		g.drawOval(cellWidth*column + (offset*cellWidth)/2, cellHeight*row, cellWidth, cellHeight);
		g.setColor(colorMap.get(color));
		g.fillOval(cellWidth*column + (offset*cellWidth)/2, cellHeight*row, cellWidth, cellHeight);
	}
	
	public Color getColor() {
		return colorMap.get(color);
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

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	
}
