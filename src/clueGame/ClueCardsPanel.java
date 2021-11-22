package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueCardsPanel extends JPanel {
	
	private JPanel people;
	private JPanel rooms;
	private JPanel weapons;
	private JPanel peopleSeen;
	private JPanel roomsSeen;
	private JPanel weaponsSeen;
	JPanel peopleInHand;
	JPanel roomsInHand;
	JPanel weaponsInHand;
	
	
	public ClueCardsPanel(ArrayList<Card> hand) {
		people = new JPanel();
		rooms = new JPanel();
		weapons = new JPanel();
		peopleSeen = new JPanel();
		roomsSeen = new JPanel();
		weaponsSeen = new JPanel();
		
		// Each JPanel will have In Hand and Seen panels
		people.setLayout(new GridLayout(2, 0));
		rooms.setLayout(new GridLayout(2, 0));
		weapons.setLayout(new GridLayout(2, 0));
		
		ArrayList<Card> peopleHand = new ArrayList<Card>();
		ArrayList<Card> roomHand = new ArrayList<Card>();
		ArrayList<Card> weaponHand = new ArrayList<Card>();
		
		// Finds the cards of each type in the player's hand
		for (Card card : hand) {
			if (card.getType() == CardType.PERSON) {
				peopleHand.add(card);
			}
			else if (card.getType() == CardType.ROOM) {
				roomHand.add(card);
			}
			else {
				weaponHand.add(card);
			}
		}
		
		setLayout(new GridLayout(3, 1));
		
		// Creates JPanel for each type of card
		peopleInHand = createInHand(peopleHand);
		roomsInHand = createInHand(roomHand);
		weaponsInHand = createInHand(weaponHand);
		
		// Adds In Hand JPanels to their respective container JPanels
		people.add(peopleInHand);
		rooms.add(roomsInHand);
		weapons.add(weaponsInHand);
		
		updateSeen(new ArrayList<Card>());
		
		
		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		rooms.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		weapons.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		
		// Adds all JPanels to KnownCards JPanel
		add(people);
		add(rooms);
		add(weapons);
	}
	
	/**
	 * Creates JPanel for a specific type of card. Will create "None" text field if the player has none of the type passed in
	 * @param cardsInHand: a list of cards of a one type (PERSON, ROOM, WEAPON) that the player has in their hand
	 * @return JPanel with the cards displayed
	 */
	private JPanel createInHand(ArrayList<Card> cardsInHand) {
		JPanel cardsHand =  new JPanel();
		JTextField inHandText;
		JLabel inHand;
		
		// If the player has at least one of card of the type passed in
		if (cardsInHand.size() >= 1) {
			cardsHand.setLayout(new GridLayout(cardsInHand.size()+1, 0));
			inHand = new JLabel("In Hand:");
			cardsHand.add(inHand);
			for (Card person : cardsInHand) {
				inHandText = new JTextField(15);
				inHandText.setEditable(false);
				inHandText.setText(person.getName());
				cardsHand.add(inHandText);
			}
		}
		else { // The player has no cards of the type passed in
			cardsHand.setLayout(new GridLayout(2, 0));
			inHand = new JLabel("In Hand:");
			cardsHand.add(inHand);
			inHandText = new JTextField(15);
			inHandText.setEditable(false);
			inHandText.setText("None");
			cardsHand.add(inHandText);
		}
		
		return cardsHand;
	}
	
	
	// Check card type, then add to peopleSeen, roomsSeen, or weaponsSeen
	// Then add peopleSeen, roomsSeen, and weaponsSeen to people, rooms, and weapons
	public void updateSeen(ArrayList<Card> seenCards) {
		
		
		// Removes the current layout of cards
		people.remove(peopleSeen);
		rooms.remove(roomsSeen);
		weapons.remove(weaponsSeen);
		
		peopleSeen = new JPanel();
		roomsSeen = new JPanel();
		weaponsSeen = new JPanel();
		
		// Adds Space for seen cards
		JLabel seenPeopleLabel = new JLabel("Seen:");
		JLabel seenRoomsLabel = new JLabel("Seen:");
		JLabel seenWeaponsLabel = new JLabel("Seen:");
		
		peopleSeen.add(seenPeopleLabel);
		roomsSeen.add(seenRoomsLabel);
		weaponsSeen.add(seenWeaponsLabel);
		
		JTextField seenPeople = new JTextField(15);;
		JTextField seenRooms = new JTextField(15);;
		JTextField seenWeapons = new JTextField(15);;
		
		int peopleCount = 0;
		int roomCount = 0;
		int weaponCount = 0;
		
		// Goes through each seen cards and adds them
		for (Card seen: seenCards) {
			if (seen == null) {
				break;
			}
			else if (seen.getType() == CardType.PERSON) {
				seenPeople = new JTextField(15);
				seenPeople.setEditable(false);
				seenPeople.setText(seen.getName());
				peopleSeen.add(seenPeople);
				peopleCount++;
			}
			else if (seen.getType() == CardType.ROOM) {
				seenRooms = new JTextField(15);
				seenRooms.setEditable(false);
				seenRooms.setText(seen.getName());
				roomsSeen.add(seenRooms);
				roomCount++;
			}
			else {
				seenWeapons = new JTextField(15);
				seenWeapons.setEditable(false);
				seenWeapons.setText(seen.getName());
				weaponsSeen.add(seenWeapons);
				weaponCount++;
			}
		}
		
		
		// Checks to see if room,person, or weapon is empty
		if (peopleCount == 0) {
			seenPeople = new JTextField(15);
			seenPeople.setEditable(false);
			seenPeople.setText("None");
			peopleSeen.add(seenPeople);
			peopleCount++;
		}
		if (roomCount == 0) {
			seenRooms = new JTextField(15);
			seenRooms.setEditable(false);
			seenRooms.setText("None");
			roomsSeen.add(seenRooms);
			roomCount++;
		}
		if (weaponCount == 0) {
			seenWeapons = new JTextField(15);
			seenWeapons.setEditable(false);
			seenWeapons.setText("None");
			weaponsSeen.add(seenWeapons);
			weaponCount++;
		}
		
		// Resets the layout based on number of cards
		peopleSeen.setLayout(new GridLayout(peopleCount+1, 1));
		roomsSeen.setLayout(new GridLayout(roomCount+1, 1));
		weaponsSeen.setLayout(new GridLayout(weaponCount+1, 1));
		
		
		// Adds the panels and repaints them
		people.add(peopleSeen);
		rooms.add(roomsSeen);
		weapons.add(weaponsSeen);
		revalidate();
		repaint();
			
	}
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Create test player to display their cards and the cards they have seen
		Player testPlayer = new HumanPlayer("Mike", "Blue", 17, 0);
		testPlayer.updateHand(new Card("Bedroom", "ROOM"));
		testPlayer.updateHand(new Card("Pistol", "WEAPON"));
		testPlayer.updateHand(new Card("Laundry Room", "ROOM"));
		
		// Update cards in the seen
		testPlayer.updateSeen(new Card("Dagger", "WEAPON"));
		testPlayer.updateSeen(new Card("Sun Room", "ROOM"));
		testPlayer.updateSeen(new Card("Olivia", "PERSON"));
		testPlayer.updateSeen(new Card("Liam", "PERSON"));
		
		ClueCardsPanel knownCards = new ClueCardsPanel(testPlayer.getHand());
		knownCards.setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));
		
		knownCards.updateSeen(testPlayer.getSeen());
		
		JFrame frame = new JFrame();
		frame.setContentPane(knownCards);
		frame.setSize(180, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
