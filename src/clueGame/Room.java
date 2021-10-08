package clueGame;

public class Room {
	
	// Instance variables
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	// Default constructor
	public Room(){
		
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


}
