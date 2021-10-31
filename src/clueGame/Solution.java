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
	
	
}
