package clueGame;

import javax.swing.JFrame;

public class ClueGame extends JFrame {

	private static Board board;
	
	public static void main(String[] args) {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		JFrame clueGame = new JFrame();
		clueGame.setContentPane(board);
		clueGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clueGame.setSize(500, 500);
		clueGame.setVisible(true);
	}

}
