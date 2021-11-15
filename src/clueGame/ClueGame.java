package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClueGame extends JFrame {

	private static Board board;
	private static Player human;
	private static int currPlayerNum;
	private static GameControlPanel gameControl;
	private static ClueCardsPanel cardPanel;
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

				if (notSeen.size() == 3) {
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
						// TODO exit game or prompt to play again
					}
				}
				else {
					BoardCell computerTarget = ((ComputerPlayer) currPlayer).selectTarget(board.getTargets(), board);
					currPlayer.setRow(computerTarget.getRow());
					currPlayer.setCol(computerTarget.getCol());
					board.resetTargets();
					board.repaint();
				}
				currPlayer.getSeen().size();
			}
		}
		else { // If the player hasn't finished their turn, print error message
			JOptionPane.showMessageDialog(this, "Please finish turn before clicking next.");
		}
	}

	public static void proccessBoardClick(int row, int col) {
		Player currPlayer = board.getPlayers().get(currPlayerNum);
		
		if (currPlayer instanceof HumanPlayer) {
			Set<BoardCell> targets = board.getTargets();
			BoardCell click = board.getCell(row, col);
			if (targets.contains(click)) {
				currPlayer.setRow(row);
				currPlayer.setCol(col);
				board.resetTargets();
				targets.clear();
				board.setTargets(targets);
				board.repaint();
				humanFinished = true;
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
