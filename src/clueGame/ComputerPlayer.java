package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class ComputerPlayer extends Player {
	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}
	
	@Override
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	// Creates a suggestion based off room and seen cards
	public Solution createSuggestion(Board board) {
		ArrayList<Card> deck = board.getDeck();
		BoardCell cell = board.getCell(row,column);
		Room room = board.getRoom(cell);
		String roomName = room.getName();
		
		// Create the correct room
		Card suggestedRoom = new Card(roomName, "ROOM");
		
		Random rand = new Random();
		
		
		Card suggestedPerson = deck.get(rand.nextInt(Board.NUM_PLAYERS)+Board.NUM_ROOMS);
		while(seen.contains(suggestedPerson)) {
			suggestedPerson = deck.get(rand.nextInt(Board.NUM_PLAYERS)+Board.NUM_ROOMS);
		}
		
		Card suggestedWeapon = deck.get(rand.nextInt(deck.size()-15)+15);
		while(seen.contains(suggestedWeapon)) {
			suggestedWeapon = deck.get(rand.nextInt(deck.size()-15)+15);
		}

		return new Solution(suggestedRoom, suggestedPerson, suggestedWeapon);
	}
	
	public BoardCell selectTarget(Set<BoardCell> targets, Board board) {
		Set<BoardCell> rooms = new HashSet<BoardCell>();
		Set<BoardCell> roomsSeen = new HashSet<BoardCell>();
		Random rand = new Random();
		
		for (BoardCell target : targets) {
			if (target.isRoomCenter()) {
				rooms.add(target);
			}
			for (Card seen : this.seen) {
				Room currRoom = board.getRoom(target.getInitial());
				String currRoomName = currRoom.getName();
				if (seen.equals(new Card(currRoomName, "ROOM"))) {
					roomsSeen.add(target);
				}
			}
		}
		
		rooms.removeAll(roomsSeen);
		
		if (rooms.size() == 0) {
			int randIdx = rand.nextInt(targets.size());
			Object[] randTargets = targets.toArray();
			return (BoardCell) randTargets[randIdx];
		}
		else {
			int randIdx = rand.nextInt(rooms.size());
			Object[] randTargets = rooms.toArray();
			return (BoardCell) randTargets[randIdx];
		}
		
	}
}
