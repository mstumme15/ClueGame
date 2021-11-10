package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClueGame extends JFrame {

	private static Board board;
	
	public static void main(String[] args) {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		GameControlPanel gameControl = new GameControlPanel();
//		ClueCardsPanel clueCards = new ClueCardsPanel();
		
		JPanel game = new JPanel();
		game.setLayout(new GridLayout(2, 2));
		game.add(board, BorderLayout.CENTER);
		game.add(gameControl, BorderLayout.SOUTH);
		
		JFrame clueGame = new JFrame();
		clueGame.setContentPane(game);
		clueGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clueGame.setSize(500, 500);
		clueGame.setVisible(true);
	}

}
