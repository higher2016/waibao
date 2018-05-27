import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

public class PuzzleTest {
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

	public PuzzleTest() {
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

		while (true) {
			System.out.println();
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

		while (true) {
			System.out.println();
			if (initiatelvl1() == true) {
				startTime = System.currentTimeMillis();
				break;
			}
		}

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

		while (true) {
			System.out.println();
			if (initiatelvl2() == true) {
				startTime = System.currentTimeMillis();
				break;
			}
		}

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

	public JComponent getPanel() {
		return lvl1.getPanel();
	}

	public JFrame getWindow() {
		return score.getWindow();
	}

	public JFrame getWindow3() {
		return score.getWindow3();
	}

	public JFrame getWindow4() {
		return lvl2.getWindow();
	}

	public boolean initiatelvl1() {
		return lvl1.initiatelvl1();
	}

	public boolean initiatelvl2() {
		return lvl2.initiatelvl2();
	}

	public JTable getTable() {
		return score.getTable();
	}

	public static void main(String[] args) {
		PuzzleTest puzzle = new PuzzleTest();
	}
}