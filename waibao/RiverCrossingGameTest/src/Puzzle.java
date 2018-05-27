import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 * uses the java swing APIs to implement a simple replica of the plank puzzle
 * game. To let the program check for the end of each level and the start of a
 * new, several 'forever' loops have been implemented
 *
 * version 2.0
 *
 * @Author Petros Soutzis (p.soutzis@lancaster.ac.uk)
 */

public class Puzzle {
	private final int SCREEN_WIDTH = 9 * 50;
	private final int SCREEN_HEIGHT = 13 * 50;
	private final int ROWS = 9;
	private final int COLS = 13;
	private Level1 lvl1;
	private Level2 lvl2;
	private Score score;
	private JPanel panel;
	private JFrame window;
	private JFrame level3;
	private long startTime;
	private long endTime;
	private long totalTime;

	/**
	 * Creates a new instance of the Level1, Level2, Score classes. It also
	 * creates instances of a JFrame for running Level1 and a JFrame and a
	 * JPanel to run Level 3.
	 *
	 * It sets up the windows in which Level 1 and Level3 will run into. It
	 * calls the methods imgInit(), startLevel1(), mouseListener(),
	 * startLevel2(), setupTable() and instructionsWindow().
	 *
	 */
	public Puzzle() {
		lvl1 = new Level1();
		lvl2 = new Level2();
		score = new Score();
		window = new JFrame();
		panel = new JPanel();
		level3 = new JFrame();

		JFrame.setDefaultLookAndFeelDecorated(true);
		window.setTitle("Puzzle Week25_SCC110 - Level1");
		window.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		window.setContentPane(getPanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLayout(new GridLayout(ROWS, COLS));

		level3.setTitle("Puzzle Week25_SCC110 - Level3 ___NOT SUPPORTED YET___");
		level3.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		level3.setContentPane(panel);
		level3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		lvl1.imgInit();
		lvl1.startLevel1();
		lvl1.mouseListener();

		score.setupTable();
		score.instructionsWindow();

		lvl2.imgInit();
		lvl2.startLevel2();
		lvl2.mouseListener();

		/**
		 * Checks if the play or instructions button is pressed and starts game
		 * or displays the instructions accordingly
		 */
		while (true) {
			if (score.showInstr == true) {
				getWindow3().setVisible(true);
				score.showInstr = false;
			}
			if (score.startTheGame == true) {
				window.setVisible(true);
				getWindow().setVisible(false);
				getWindow3().setVisible(false);
				score.startTheGame = false;
				break;
			}
		}

		/**
		 * Checks if the user is playing the game and starts counting the time
		 * that the user has been playing, until the end of the level
		 */
		while (true) {
			System.out.println();
			if (initiatelvl1() == true) {
				startTime = System.currentTimeMillis();
				break;
			}
		}

		/**
		 * When the level ends, it stores the total time that the user played
		 * and displays it on the score table, which is now the only visible
		 * window on the screen
		 */
		while (true) {
			System.out.println();
			if (initiatelvl1() == false) {
				endTime = System.currentTimeMillis();
				totalTime = (endTime - startTime) / 1000;
				getTable().setValueAt("Level1", 1, 0);
				getTable().setValueAt(totalTime + " seconds", 1, 1);
				window.setVisible(false);
				getWindow().setVisible(true);
				break;
			}
		}

		/**
		 * Checks if the play or instructions button is pressed and starts the
		 * level or displays the instructions accordingly
		 */
		while (true) {
			System.out.println();
			if (score.showInstr == true) {
				getWindow3().setVisible(true);
				score.showInstr = false;
			}
			if (score.startTheGame == true) {
				getWindow4().setVisible(true);
				getWindow().setVisible(false);
				getWindow3().setVisible(false);
				score.startTheGame = false;
				break;
			}
		}

		/**
		 * Checks if the user is playing the game and starts counting the time
		 * that the user has been playing, until the end of the level
		 */
		while (true) {
			System.out.println();
			if (initiatelvl2() == true) {
				startTime = System.currentTimeMillis();
				break;
			}
		}

		/**
		 * When the level ends, it stores the total time that the user played
		 * and displays it on the score table, which is now the only visible
		 * window on the screen
		 */
		while (true) {
			System.out.println();
			if (initiatelvl2() == false) {
				endTime = System.currentTimeMillis();
				totalTime = (endTime - startTime) / 1000;
				getTable().setValueAt("Level2", 2, 0);
				getTable().setValueAt(totalTime + " seconds", 2, 1);
				getWindow4().setVisible(false);
				getWindow().setVisible(true);
				break;
			}
		}

		/**
		 * Checks if the play or instructions button is pressed and starts the
		 * level or displays the instructions accordingly IN THIS CASE - LEVEL 3
		 * IS NOT SUPPORTED*********
		 */
		while (true) {
			System.out.println();
			if (score.showInstr == true) {
				getWindow3().setVisible(true);
				score.showInstr = false;
			}
			if (score.startTheGame == true) {
				level3.setVisible(true);
				getWindow().setVisible(false);
				getWindow3().setVisible(false);
				score.startTheGame = false;
				System.out.println("Level 3 is not supported yet");
				break;
			}
		}

	}

	/**
	 * Provides a Swing component in which the Racer game runs. This component
	 * can be added to a Swing panel to display the game on screen.
	 *
	 * @return A Swing component for this game.
	 */
	public JComponent getPanel() {
		return lvl1.getPanel();
	}

	/**
	 * Provides the JFrame that the JTable for the user's score is using
	 * 
	 * @return The window that holds the scores table
	 */
	public JFrame getWindow() {
		return score.getWindow();
	}

	/**
	 * Provides the JFrame that is used to display the Instructions of the game
	 * 
	 * @return The window that the instructions are on.
	 */
	public JFrame getWindow3() {
		return score.getWindow3();
	}

	/**
	 * Provides the JFrame holding Level 2.
	 * 
	 * @return The window that is used to view Level 2
	 */
	public JFrame getWindow4() {
		return lvl2.getWindow();
	}

	/**
	 * Determines if Level 1 has started or not
	 * 
	 * @return true if user is playing level 1 and false when the user has
	 *         fininshed level 1 or hasn't started it yet
	 */
	public boolean initiatelvl1() {
		return lvl1.initiatelvl1();
	}

	/**
	 * Determines if Level 2 has started or not
	 * 
	 * @return true if user is playing level 2 and false when the user has
	 *         fininshed level 2 or hasn't started it yet
	 */
	public boolean initiatelvl2() {
		return lvl2.initiatelvl2();
	}

	/**
	 * Provides the JTable holding the time records and the Instructions of the
	 * game.
	 * 
	 * @return The table with the player's score.
	 */
	public JTable getTable() {
		return score.getTable();
	}

	/**
	 * 
	 * @param args
	 *            are unused
	 */
	public static void main(String[] args) {
		Puzzle puzzle = new Puzzle();
	}
}