import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

/**
 * 
 * The Score class, is a class that holds the Score Table and the instructions
 * window It implements ActionListener, on the Play and Instructions Buttons
 *
 * 
 * @Author Petros Soutzis (p.soutzis@lancaster.ac.uk)
 */

public class Score implements ActionListener {
	private JTable table;
	private JFrame window2;
	private JFrame window3;
	private JLabel instr;
	private JButton butt;
	private JButton butt2;
	private JPanel panel;
	public boolean startTheGame = false;
	public boolean showInstr = false;
	private String howToPlay = "<html><b><font color='#922b21'>The Instructions to the most stressful coursework of 'Summer' term, which is this Puzzle Game, are simple:&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</font></b><br><br><font color='#cb4335'>~Use the left mouse click to move your player and the right mouse click to pick up any plank.<br>~If you're using a touch-screen device, tap for player to move or tap and hold for player to pick the plank up.<br>~The player can carry only 1 single or double plank at a time.<br>~Try and reach the other side of the river as quick as possible.<br><br><br><br>~Best time wins !!<br><br><br><br><br><br>~Good Luck!</font><br></html>";

	public Score() {
		table = new JTable(11, 2);
		window2 = new JFrame();
		window3 = new JFrame();
		instr = new JLabel("", SwingConstants.CENTER);
		butt = new JButton("START");
		butt2 = new JButton("INSTRUCTIONS");
		panel = new JPanel();

	}

	/**
	 * Provides the JFrame that the JTable for the user's score is using
	 * 
	 * @return The window that holds the scores table
	 */
	public JFrame getWindow() {
		return window2;
	}

	/**
	 * Provides the JFrame that is used to display the Instructions of the game
	 * 
	 * @return The window that the instructions are on.
	 */
	public JFrame getWindow3() {
		return window3;
	}

	/**
	 * This sets up the score table and the window holding it a new
	 * ActionListener() is added to the 2 buttons that are on the window
	 */
	public void setupTable() {

		table.setRowHeight(72);
		table.setPreferredSize(new java.awt.Dimension(500, 500));
		table.setValueAt("                     LEVEL", 0, 0);
		table.setValueAt("                     SCORE", 0, 1);
		table.setFont(new Font("Arial", Font.BOLD, 30));
		table.setEnabled(false);
		window2.add(butt);
//		window2.add(butt2);
		butt.setSize(500, 110);
		butt.setFont(new Font("Arial", Font.PLAIN, 30));
		butt.setLocation(0, 790);
		butt.addActionListener(this);
		butt2.setSize(500, 110);
		butt2.setFont(new Font("Arial", Font.PLAIN, 30));
		butt2.setLocation(500, 790);
		butt2.addActionListener(this);

		window2.setLocation(1200, 500);
		window2.setSize(1000, 1000);
		window2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window2.setResizable(false);
//		window2.add(table);
		window2.pack();
		window2.setVisible(true);
	}

	/**
	 * This sets up the label that the instructions are written on and the
	 * window that holds it. No default close operation added, because this is
	 * the instructions window.
	 */
	public void instructionsWindow() {
		instr.setText(howToPlay);
		instr.setFont(new Font("Arial", Font.ITALIC, 40));
		window3.setLayout(new BorderLayout());
		window3.setLocation(1200, 500);
		window3.setSize(1500, 1500);
		window3.setResizable(false);
		window3.add(panel);
		panel.add(instr);
		window3.pack();
	}

	/**
	 * Provides the boolean that starts the game each time
	 * 
	 * @return true if Play button is pressed
	 */
	public boolean getStart() {
		return startTheGame;
	}

	/**
	 * Provides JTable that is used as the score table
	 * 
	 * @return score table JTable
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * "Listens" to the buttons and if they are pressed the analogous actions
	 * are taken
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == butt) {
			startTheGame = true;
		}

		if (e.getSource() == butt2) {
			showInstr = true;
		}
	}
}