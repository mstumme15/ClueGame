package clueGame;

public abstract class Player{

	private String name;
	private String color;
	protected int row;
	protected int column;
	
	public Player(String name, String color, int row, int column) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
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
	
}
