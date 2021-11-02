package clueGame;

public class Solution {
	
	private Card room;
	private Card person;
	private Card weapon;
	
	
	//Constructor to set the solution to the game
	public Solution(Card room, Card person, Card weapon) {
		super();
		this.room = room;
		this.person = person;
		this.weapon = weapon;
	}


	public Card getRoom() {
		return room;
	}


	public Card getPerson() {
		return person;
	}


	public Card getWeapon() {
		return weapon;
	}
	
	//Overide the equals method in order to see if list of players contains certian values
	@Override 
	public boolean equals(Object p){
		Solution o = (Solution) p;
        if (this.room.equals(o.room) && this.person.equals(o.person) && this.weapon.equals(o.weapon)) {
            return true;
        }
        else {
            return false;
        }
    }
	
	
}
