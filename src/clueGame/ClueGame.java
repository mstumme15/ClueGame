package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Random;

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
		gameControl = new GameControlPanel();
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
			if (currPlayer.getName().equals(human.getName())) {
				humanFinished = false;
				board.paint(board.getGraphics());
			}
		}
		else { // If the player hasn't finished their turn, print error message
			JOptionPane.showMessageDialog(this, "Please finish turn before clicking next.");
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
