package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
	public Card (String name, String type) {
		super();
		cardName = name;
		this.type = CardType.valueOf(type);
	}
	
	
	//Overide the equals method in order to see if list of cards contains certain values
	@Override 
	public boolean equals(Object target){
		Card o = (Card) target;
        if (this.cardName.equals(o.cardName) && this.type.equals(o.type)) {
            return true;
        }
        else {
            return false;
        }
    }
	
	// Getters and setters 
	public String getName() {
		return cardName;
	}

	public CardType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Card [cardName=" + cardName + ", type=" + type + "]";
	}
	
	
	
}
