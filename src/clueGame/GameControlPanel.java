package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {

	private JTextField turn;
	private JTextField roll;
	private JTextField guess;
	private JTextField guessResult;
	private Player currPlayer;
	private ClueGame gameInstance;
	
	// Constructor creates the game control panel
	public GameControlPanel(ClueGame gameInstance)  {
		this.gameInstance = gameInstance;
		
		setLayout(new GridLayout(2,0));
		JPanel panel = createTurn();
		add(panel);
		
		panel = createGuesses();
		add(panel);
	}
	
	//Creates the first row of the game control panel
	private JPanel createTurn() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,4));
		
		// Create the whos turn field
		JPanel panelName = new JPanel();
		panelName.setLayout(new GridLayout(2,0));
		JLabel nameLabel = new JLabel("Who's Turn");
		turn = new JTextField(20);
		turn.setEditable(false);
		turn.setText("Testing");
		panelName.add(nameLabel);
		panelName.add(turn);
		
		//Create dice roll
		JPanel dicePanel = new JPanel();
		JLabel rollLabel = new JLabel("Roll:");
		roll = new JTextField(5);
		roll.setEditable(false);
		roll.setText("1");
		dicePanel.add(rollLabel);
		dicePanel.add(roll);
		
		//Create Accusation button
		JButton accusation = new JButton("Make Accusation");
		
		
		//Create next button
		JButton next = new JButton("NEXT!");
		next.addActionListener(new ButtonListener());
		
		// Add the 2 panels and 2 buttons 
		panel.add(panelName);
		panel.add(dicePanel);
		panel.add(accusation);
		panel.add(next);
		
		return panel;
	}
	
	// Creates the second row of the game control panel
	private JPanel createGuesses() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		
		// Add guess panel
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(0,1));
		guess = new JTextField(20);
		guess.setEditable(false);
		guess.setText("Testing");
		guessPanel.add(guess);
		guessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		
		// Add guess panel
		JPanel guessResultPanel = new JPanel();
		guessResultPanel.setLayout(new GridLayout(0,1));
		guessResult = new JTextField(20);
		guessResult.setEditable(false);
		guessResult.setText("Testing");
		guessResultPanel.add(guessResult);
		guessResultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		
		//Add both panels to the larger panel
		panel.add(guessPanel);
		panel.add(guessResultPanel);
		
		return panel;
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			gameInstance.processNext(currPlayer);
		}
	}
	
//	/**
//	 * Main to test the panel
//	 * 
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		GameControlPanel panel = new GameControlPanel();  // create the panel
//		JFrame frame = new JFrame();  // create the frame 
//		frame.setContentPane(panel); // put the panel in the frame
//		frame.setSize(750, 180);  // size the frame
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
//		frame.setVisible(true); // make it visible
//		
//		// test filling in the data
//		panel.setTurn(new ComputerPlayer( "Col. Mustard","orange",0,0), 5);
//		panel.setGuess( "I have no guess!");
//		panel.setGuessResult( "So you have nothing?");
//	}

	public void setGuessResult(String string) {
		guessResult.setText(string);
	}

	public void setGuess(String string) {
		guess.setText(string);
	}

	public void setTurn(Player player, int i) {
		currPlayer = player;
		turn.setText(player.getName());
		roll.setText(Integer.toString(i));
	}
	
	public void setResultColor(Color color) {
		guessResult.setBackground(color);
	}
	
	public void setTurnColor(Color color) {
		turn.setBackground(color);
	}
}