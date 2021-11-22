package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClueGame extends JFrame {

	private static Board board;
	private static Player human;
	private static int currPlayerNum;
	private static GameControlPanel gameControl;
	private static ClueCardsPanel cardPanel;
	private static SuggestionPanel suggestion;
	protected static AccusationPanel accusation;
	private JTextField room;
	private Card currentRoom;
	private ArrayList<Card> deck;
	
	private JComboBox<Card> people;
	private JComboBox<Card> weapons;
	private JComboBox<Card> accuseRooms;
	private JComboBox<Card> accusePeople;
	private JComboBox<Card> accuseWeapons;
	private static boolean humanFinished;
	private static final int NUM_PLAYERS = 6;

	public ClueGame() {
		currPlayerNum = NUM_PLAYERS-1;
		humanFinished = true;

		// Set basic Jframe functionality
		setTitle("Clue Game");
		setSize(750,750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();

		human = board.getPlayers().get(0);
		gameControl = new GameControlPanel(this);
		cardPanel = new ClueCardsPanel(human.getHand());
		suggestion = new SuggestionPanel();
		accusation = new AccusationPanel();

		add(board, BorderLayout.CENTER);
		add(gameControl, BorderLayout.SOUTH);
		add(cardPanel, BorderLayout.EAST);

	}

	public int rollDice() {
		Random rand = new Random();
		int roll = rand.nextInt(6);
		return roll+1;
	}

	// Process the actions after next button is clicked
	public void processNext(Player currPlayer) {

		// If the human player has finished their turn 
		if (humanFinished) {
			
			// Go to next player
			currPlayerNum = (currPlayerNum + 1) % NUM_PLAYERS;
			currPlayer = board.getPlayers().get(currPlayerNum);
			gameControl.setTurnColor(currPlayer.getColor());
			gameControl.setGuess("");
			gameControl.setGuessResult("");
			gameControl.setResultColor(Color.WHITE);
			int roll = rollDice();
			int playerRow = currPlayer.getRow();
			int playerCol = currPlayer.getColumn();
			
			// Calculate targets for current player
			BoardCell playerLoc = board.getCell(playerRow, playerCol);
			board.calcTargets(playerLoc, roll);
			
			// Update game control panel with new player and roll
			gameControl.setTurn(currPlayer, roll);

			// If the current player is the human player
			if (currPlayer instanceof HumanPlayer) {
				humanFinished = false;
				board.repaint();
			}
			else if (currPlayer instanceof ComputerPlayer){
				ArrayList<Card> deck = board.getDeck();
				ArrayList<Card> notSeen = new ArrayList<Card>();
				ArrayList<Card> seen = currPlayer.getSeen();
				for (Card handCard : currPlayer.getHand()) {
					seen.add(handCard);
				}
				for (Card seenCard : seen) {
					if (!deck.contains(seenCard)) {
						notSeen.add(seenCard);
					}
				}

				if (notSeen.size() == 3) { // Make accusation if only 3 cards are not seen
					Card roomAccuse = null;
					Card personAccuse = null;
					Card weaponAccuse = null;
					for (Card card : notSeen) {
						if (card.getType() == CardType.ROOM) {
							roomAccuse = card;
						}
						else if (card.getType() == CardType.PERSON) {
							personAccuse = card;
						}
						else {
							weaponAccuse = card;
						}
					}
					Solution accusation = new Solution(roomAccuse, personAccuse, weaponAccuse);
					if (board.checkAccusation(accusation)) {
						JOptionPane.showMessageDialog(this, currPlayer.getName()+" successfully guessed the solution.");
						System.exit(0);
					}
				}
				else { // Move computer player
					BoardCell computerTarget = ((ComputerPlayer) currPlayer).selectTarget(board.getTargets(), board);
					BoardCell currLocation = board.getCell(currPlayer.getRow(), currPlayer.getColumn());
					currLocation.setOccupied(false);
					
					// Set the new computer target to occupied
					currPlayer.setRow(computerTarget.getRow());
					currPlayer.setCol(computerTarget.getCol());
					computerTarget.setOccupied(true);
					board.resetTargets();
					board.repaint();
					
					// computer makes suggestion if in room
					if (computerTarget.isRoomCenter()) {
						Solution suggestion = ((ComputerPlayer) currPlayer).createSuggestion(board);
						Card disprove = board.handleSuggestion(suggestion.getRoom(), suggestion.getPerson(), suggestion.getWeapon(), currPlayer, gameControl);
						if (disprove == null) {
							((ComputerPlayer) currPlayer).setMakeAccusation(true);
						}
						else {
							currPlayer.updateSeen(disprove);
						}
					}
					
					
				}
				currPlayer.getSeen().size();
			}
		}
		else { // If the player hasn't finished their turn, print error message
			JOptionPane.showMessageDialog(this, "Please finish turn before clicking next.");
		}
	}

	// Processes when the board is clicked
	public static void processBoardClick(int row, int col) {
		Player currPlayer = board.getPlayers().get(currPlayerNum);
		
		// Checks to see if it is the human players turn
		if (currPlayer instanceof HumanPlayer) {
			Set<BoardCell> targets = board.getTargets();
			BoardCell click = board.getCell(row, col);
			
			//Checks to see if click is one of the targets
			if (targets.contains(click)) {
				// Changes the players current location to not occupied
				BoardCell currLocation = board.getCell(currPlayer.getRow(), currPlayer.getColumn());
				currLocation.setOccupied(false);
				
				// Change player new location to occupied
				currPlayer.setRow(row);
				currPlayer.setCol(col);
				click.setOccupied(true);
				board.resetTargets();
				targets.clear();
				board.setTargets(targets);
				
				// Moves the player 
				board.repaint();
				
				// Check to see if target clicked was a room
				if (click.isRoomCenter()) {
					suggestion.setRoom(board.getRoom(click).getName());
					suggestion.setVisible(true);
				}
				
				humanFinished = true; // Signals target was selected
			}
			
		}
	}
	
	private class SuggestionPanel extends JDialog {
		public SuggestionPanel() {
			setTitle("Make a suggestion");
			setSize(300,200);
			setLayout(new GridLayout(4,2));
			JLabel roomLabel = new JLabel("Room");
			JLabel personLabel = new JLabel("Person");
			JLabel weaponLabel = new JLabel("Weapon");
			
			room = new JTextField();
			room.setEditable(false);
			people = new JComboBox<Card>();
			weapons = new JComboBox<Card>();
			
			JButton submit = new JButton("Submit");
			JButton cancel = new JButton("Cancel");
			
			submit.addActionListener(new SubmitListener());
			cancel.addActionListener(new CancelListener());
			
			deck = board.getDeck();
			
			for (Card card: deck) {
				if (card.getType() == CardType.PERSON) {
					people.addItem(card);
				}
				else if (card.getType() == CardType.WEAPON) {
					weapons.addItem(card);
				}
			}
			
			add(roomLabel);
			add(room);
			add(personLabel);
			add(people);
			add(weaponLabel);
			add(weapons);
			add(cancel);
			add(submit);
			setVisible(false);
			setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		}
		
		// Getters and setters
		public void setRoom(String roomName) {
			room.setText(roomName);
		}
		
		public Card getPerson() {
			return (Card) people.getSelectedItem();
		}
		
		public Card getWeapon() {
			return (Card) weapons.getSelectedItem();
		}
		
		public Card getRoom() {
			for (Card card: deck) {
				if (room.getText().equals(card.getName())){
					return card;
				}
			}
			return null;
		}
		
		public class SubmitListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				suggestion.setVisible(false);
				Card disprove = board.handleSuggestion(getRoom(), getPerson(), getWeapon(), human, gameControl);
				
				human.updateSeen(disprove);
				cardPanel.updateSeen(human.getSeen());
			}
		}
		
		public class CancelListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				suggestion.setVisible(false);
			}
		}
	}
	
	class AccusationPanel extends JDialog {
		public AccusationPanel() {
			setTitle("Make an accusation");
			setSize(300,200);
			setLayout(new GridLayout(4,2));
			JLabel roomLabel = new JLabel("Room");
			JLabel personLabel = new JLabel("Person");
			JLabel weaponLabel = new JLabel("Weapon");
			
			accuseRooms = new JComboBox<Card>();
			accusePeople = new JComboBox<Card>();
			accuseWeapons = new JComboBox<Card>();
			
			JButton submit = new JButton("Submit");
			JButton cancel = new JButton("Cancel");
			
			submit.addActionListener(new SubmitListener());
			cancel.addActionListener(new CancelListener());
			
			deck = board.getDeck();
			
			for (Card card: deck) {
				if (card.getType() == CardType.PERSON) {
					accusePeople.addItem(card);
				}
				else if (card.getType() == CardType.WEAPON) {
					accuseWeapons.addItem(card);
				}
				else if (card.getType() == CardType.ROOM) {
					accuseRooms.addItem(card);
				}
			}
			
			add(roomLabel);
			add(accuseRooms);
			add(personLabel);
			add(accusePeople);
			add(weaponLabel);
			add(accuseWeapons);
			add(cancel);
			add(submit);
			setVisible(false);
			setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		}
		
		// Getters and setters
		public Card getPerson() {
			return (Card) accusePeople.getSelectedItem();
		}
		
		public Card getWeapon() {
			return (Card) accuseWeapons.getSelectedItem();
		}
		
		public Card getRoom() {
			return (Card) accuseRooms.getSelectedItem();
		}
		
		public void setVisibilityTrue() {
			if (board.getPlayers().get(currPlayerNum) instanceof HumanPlayer && humanFinished == false) {
				accusation.setVisible(true);
			}
			else {
				JOptionPane.showMessageDialog(getRootPane(), "You can only make an accusation at the beginning of your turn.");
			}
		}
		
		public class SubmitListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				accusation.setVisible(false);
				Solution accusation = new Solution(getRoom(), getPerson(), getWeapon());
				boolean playerWon = board.checkAccusation(accusation);
				String accusationStr = accuseRooms.getSelectedItem() + ", " + accusePeople.getSelectedItem() + ", " + accuseWeapons.getSelectedItem();
				if (playerWon) {
					JOptionPane.showMessageDialog(getRootPane(), "Congratulations, " + accusationStr + " was correct! You won!");
					System.exit(0);
				}
				else {
					JOptionPane.showMessageDialog(getRootPane(), "Sorry, " + accusationStr + " was incorrect. You lost.");
					System.exit(0);
				}
			}
		}
		
		public class CancelListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				accusation.setVisible(false);
			}
		}
	}

	public static void main(String[] args) {
		ClueGame clueGame = new ClueGame();
		clueGame.setVisible(true);

		// Print opening message dialog
		JOptionPane.showMessageDialog(clueGame, "You are "+human.getName()+". Can you find the solution before the Computer players?");

		// Process initial turn
		clueGame.processNext(board.getPlayers().get(currPlayerNum));
	}
}
