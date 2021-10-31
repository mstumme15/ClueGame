package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
	public Card (String name, String type) {
		super();
		cardName = name;
		this.type = CardType.valueOf(type);
	}
	
	// equals - compares to see if two cards are equivalent
	// returns true if the cards are the same
	public boolean equals(Card target) {
		return target.cardName.equals(cardName);
	}
	
	// Getters and setters 
	public String getName() {
		return cardName;
	}

	public CardType getType() {
		return type;
	}
	
}
