package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClueGame extends JFrame {

	private static Board board;
	
	public ClueGame() {
		// Set basic Jframe functionality
		setTitle("Clue Game");
		setSize(750,750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		Player human = board.getPlayers().get(0);
		GameControlPanel gameControl = new GameControlPanel();
		ClueCardsPanel cardPanel = new ClueCardsPanel(human.getHand());
		
		add(board, BorderLayout.CENTER);
		add(gameControl, BorderLayout.SOUTH);
		add(cardPanel, BorderLayout.EAST);
		
		
		
	}
	
	public static void main(String[] args) {
		
		
		ClueGame clueGame = new ClueGame();
		clueGame.setVisible(true);
		
		
	}

}
