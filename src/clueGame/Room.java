package clueGame;

public class Room {
	
	// Instance variables
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private char secretPassage;
	

	// Default constructor
	public Room(){
		secretPassage = '0';
	}
	
	// Getters and setters
	public String getName() {
		return name;
	}

	public BoardCell getLabelCell() {
		return labelCell;
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}
	public char getSecretPassage() {
		return secretPassage;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}


}
