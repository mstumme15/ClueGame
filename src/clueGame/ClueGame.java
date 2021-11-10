package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClueGame extends JFrame {

	private static Board board;
	
	public ClueGame() {
		setTitle("Clue Game");
		setSize(750,750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		GameControlPanel gameControl = new GameControlPanel();
		
		add(board, BorderLayout.CENTER);
		add(gameControl, BorderLayout.SOUTH);
		
		
		
	}
	
	public static void main(String[] args) {
		
		
//		ClueCardsPanel clueCards = new ClueCardsPanel();
		
		ClueGame clueGame = new ClueGame();
		clueGame.setVisible(true);
		
		
	}

}
