package clueGame;

public enum CardType {
	ROOM("room"), PERSON("person"), WEAPON("weapon");
	
	private String type;
	
	CardType(String type) {
		this.type = type;
	}
	
	public String toString() {
		return type;
	}
}
