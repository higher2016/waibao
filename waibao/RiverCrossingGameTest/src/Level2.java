import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javafx.embed.swing.JFXPanel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * 
 * Level2 class, is the class that sets up and initiates level 2 It extends
 * MouseAdapter, so the Left and Right clicks can be recorded and have different
 * actions be taken when they are clicked on the analogous JButton on the window
 *
 * @Author Petros Soutzis (p.soutzis@lancaster.ac.uk)
 */

public class Level2 extends MouseAdapter {
	private final int SCREEN_WIDTH = 9 * 50;
	private final int SCREEN_HEIGHT = 9 * 50;
	private final int ROWS = 9;
	private final int COLS = 13;
	private int xPos, yPos;
	private JFrame window;
	private JFXPanel panel;
	private Images img;
	private JButton[][] btnArray;
	private boolean canWalkFront, canWalkBack, canWalkLeft;
	private boolean heldPlankSingle = false;
	private boolean heldPlankDouble = false;
	private boolean firstEmptyEncounter = true;
	private boolean firstEmptyEncounter2 = true;
	private boolean firstEmptyEncounter3 = true;
	private boolean firstEmptyEncounter4 = true;
	private boolean gameStart = false;
	private boolean isStandingOnLog;

	public Level2() {
		panel = new JFXPanel();
		panel.setPreferredSize(new java.awt.Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		window = new JFrame();
		img = new Images();
		btnArray = new JButton[ROWS][COLS];
		addButtons();
	}

	/**
	 * Provides the JFrame holding Level 2.
	 * 
	 * @return The window that is used to view Level 2
	 */
	public JFrame getWindow() {
		return window;
	}

	/**
	 * This adds a MouseListener to every JButton in the JButton array
	 * 'btnArray'
	 */
	public void mouseListener() {
		for (int x = 0; x < ROWS; x++) {
			for (int y = 0; y < COLS; y++) {
				btnArray[x][y].addMouseListener(this);
			}
		}
	}

	/**
	 * This method, takes all the images that are used for this game, (from the
	 * Images class) and resizes them, so they can fit the JButtons when the
	 * game is run.
	 */
	public void imgInit() {
		img.Water1(); // nero
		img.Water2(); // psari
		img.Water3(); // psari_megalo
		img.Water4(); // piranxas
		img.Bank1(); // poukato
		img.Bank2(); // poupano
		img.PlankMan1(); // kormos_orizontios_athrwpos
		img.PlankMan2(); // kormos_kathetos_athrwpos
		img.Plank1(); // kormos_orizontios
		img.Plank2(); // kormos_kathetos_
		img.StumpMan1(); // koutsoro_athrwpos_nero
		img.StumpMan2(); // koutsouro_athrwpos_poukato
		img.StumpMan3(); // koutsouro_athrwpos_poupano
		img.Stump1(); // koutsouro_nero
		img.Stump2(); // koutsouro_poukato
		img.Stump3(); // koutsouro_poupano
	}

	/**
	 * This "Listens" to the buttons and if they are pressed the analogous
	 * actions are taken
	 */
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {

			if ((e.getSource() == btnArray[7][6]) || (e.getSource() == btnArray[6][6]))/**********/
			{
				if (((xPos == 8) && (yPos == 6)) && (canWalkFront == true)) {
					if ((heldPlankSingle == false) && (heldPlankDouble == false)) {
						btnArray[7][6].setIcon(img.getWater1());
						btnArray[6][6].setIcon(img.getWater1());
						canWalkFront = false;
						heldPlankDouble = true;
					}
				} else if (((xPos == 8) && (yPos == 6)) && (canWalkFront == false)) {
					if (heldPlankDouble == true) {
						btnArray[7][6].setIcon(img.getPlank2());
						btnArray[6][6].setIcon(img.getPlank2());
						canWalkFront = true;
						heldPlankDouble = false;
					}
				} else if (((xPos == 5) && (yPos == 6)) && (canWalkBack == true)) {
					if ((heldPlankSingle == false) && (heldPlankDouble == false)) {
						btnArray[7][6].setIcon(img.getWater1());
						btnArray[6][6].setIcon(img.getWater1());
						canWalkBack = false;
						heldPlankDouble = true;
					}
				} else if (((xPos == 5) && (yPos == 6)) && (canWalkBack == false)) {
					if (heldPlankDouble == true) {
						btnArray[7][6].setIcon(img.getPlank2());
						btnArray[6][6].setIcon(img.getPlank2());
						canWalkBack = true;
						heldPlankDouble = false;
					}
				}
			}
			if ((e.getSource() == btnArray[4][6]) || (e.getSource() == btnArray[3][6]))/**********/
			{
				if (((xPos == 5) && (yPos == 6)) && (canWalkFront == true) && (firstEmptyEncounter == false)) {
					if ((heldPlankDouble == false) && (heldPlankSingle == false)) {
						btnArray[4][6].setIcon(img.getWater1());
						btnArray[3][6].setIcon(img.getWater1());
						canWalkFront = false;
						heldPlankDouble = true;
					}
				} else if (((xPos == 5) && (yPos == 6)) && (canWalkFront == true) && (firstEmptyEncounter == true)) {
					if (heldPlankDouble == true) {
						btnArray[4][6].setIcon(img.getPlank2());
						btnArray[3][6].setIcon(img.getPlank2());
						heldPlankDouble = false;
						firstEmptyEncounter = false;
					}

				} else if (((xPos == 5) && (yPos == 6)) && (canWalkFront == false) && (firstEmptyEncounter == false)) {
					if (heldPlankDouble == true) {
						btnArray[4][6].setIcon(img.getPlank2());
						btnArray[3][6].setIcon(img.getPlank2());
						canWalkFront = true;
						heldPlankDouble = false;
					}
				} else if (((xPos == 2) && (yPos == 6)) && (canWalkBack == true)) {
					if ((heldPlankDouble == false) && (heldPlankSingle == false)) {
						btnArray[4][6].setIcon(img.getWater1());
						btnArray[3][6].setIcon(img.getWater1());
						canWalkBack = false;
						heldPlankDouble = true;
					}
				} else if (((xPos == 2) && (yPos == 6)) && (canWalkBack == false)) {
					if (heldPlankDouble == true) {
						btnArray[4][6].setIcon(img.getPlank2());
						btnArray[3][6].setIcon(img.getPlank2());
						canWalkBack = true;
						heldPlankDouble = false;
					}
				}
			}

			if ((e.getSource() == btnArray[5][5]) || (e.getSource() == btnArray[5][4]))/**********/
			{
				if (((xPos == 5) && (yPos == 6)) && (canWalkFront == true) && (firstEmptyEncounter2 == false)) {
					if ((heldPlankDouble == false) && (heldPlankSingle == false)) {
						btnArray[5][5].setIcon(img.getWater1());
						btnArray[5][4].setIcon(img.getWater1());
						canWalkFront = false;
						heldPlankDouble = true;
					}
				} else if (((xPos == 5) && (yPos == 6)) && (canWalkFront == true) && (firstEmptyEncounter2 == true)) {
					if (heldPlankDouble == true) {
						btnArray[5][5].setIcon(img.getPlank1());
						btnArray[5][4].setIcon(img.getPlank1());
						heldPlankDouble = false;
						firstEmptyEncounter2 = false;
					}

				} else if (((xPos == 5) && (yPos == 6)) && (canWalkFront == false) && (firstEmptyEncounter2 == false)) {
					if (heldPlankDouble == true) {
						btnArray[5][5].setIcon(img.getPlank1());
						btnArray[5][4].setIcon(img.getPlank1());
						canWalkFront = true;
						heldPlankDouble = false;
					}
				} else if (((xPos == 5) && (yPos == 3)) && (firstEmptyEncounter3 == true)) {
					if ((heldPlankDouble == false) && (heldPlankSingle == false)) {
						btnArray[5][5].setIcon(img.getWater1());
						btnArray[5][4].setIcon(img.getWater1());
						canWalkBack = false;
						heldPlankDouble = true;
					}
				} else if (((xPos == 5) && (yPos == 3)) && (canWalkBack == false)) {
					if (heldPlankDouble == true) {
						btnArray[5][5].setIcon(img.getPlank1());
						btnArray[5][4].setIcon(img.getPlank1());
						canWalkBack = true;
						heldPlankDouble = false;
					}
				}
			}

			if ((e.getSource() == btnArray[4][3]) || (e.getSource() == btnArray[3][3]))/**********/
			{
				if (((xPos == 5) && (yPos == 3)) && (canWalkFront == true) && (firstEmptyEncounter3 == false)) {
					if ((heldPlankDouble == false) && (heldPlankSingle == false)) {
						btnArray[4][3].setIcon(img.getWater1());
						btnArray[3][3].setIcon(img.getWater1());
						canWalkFront = false;
						heldPlankDouble = true;
					}
				} else if (((xPos == 5) && (yPos == 3)) && (canWalkFront == true) && (firstEmptyEncounter3 == true)) {
					if (heldPlankDouble == true) {
						btnArray[4][3].setIcon(img.getPlank2());
						btnArray[3][3].setIcon(img.getPlank2());
						heldPlankDouble = false;
						firstEmptyEncounter3 = false;
					}

				} else if (((xPos == 5) && (yPos == 3)) && (canWalkFront == false) && (firstEmptyEncounter3 == false)) {
					if (heldPlankDouble == true) {
						btnArray[4][3].setIcon(img.getPlank2());
						btnArray[3][3].setIcon(img.getPlank2());
						canWalkFront = true;
						heldPlankDouble = false;
					}
				} else if (((xPos == 2) && (yPos == 3)) && (firstEmptyEncounter4 == true)) {
					if ((heldPlankDouble == false) && (heldPlankSingle == false)) {
						btnArray[4][3].setIcon(img.getWater1());
						btnArray[3][3].setIcon(img.getWater1());
						canWalkBack = false;
						heldPlankDouble = true;
					}
				} else if (((xPos == 2) && (yPos == 3)) && (canWalkBack == false)) {
					if (heldPlankDouble == true) {
						btnArray[4][3].setIcon(img.getPlank2());
						btnArray[3][3].setIcon(img.getPlank2());
						canWalkBack = true;
						heldPlankDouble = false;
					}
				}
			}

			if ((e.getSource() == btnArray[2][4]) || (e.getSource() == btnArray[2][5]))/**********/
			{
				if (((xPos == 2) && (yPos == 3)) && (canWalkFront == true) && (firstEmptyEncounter4 == false)) {
					if ((heldPlankDouble == false) && (heldPlankSingle == false)) {
						btnArray[2][5].setIcon(img.getWater1());
						btnArray[2][4].setIcon(img.getWater1());
						canWalkFront = false;
						heldPlankDouble = true;
					}
				} else if (((xPos == 2) && (yPos == 3)) && (canWalkFront == true) && (firstEmptyEncounter4 == true)) {
					if (heldPlankDouble == true) {
						btnArray[2][5].setIcon(img.getPlank1());
						btnArray[2][4].setIcon(img.getPlank1());
						heldPlankDouble = false;
						firstEmptyEncounter4 = false;
					}

				} else if (((xPos == 2) && (yPos == 3)) && (canWalkFront == false) && (firstEmptyEncounter4 == false)) {
					if (heldPlankDouble == true) {
						btnArray[2][5].setIcon(img.getPlank1());
						btnArray[2][4].setIcon(img.getPlank1());
						canWalkFront = true;
						heldPlankDouble = false;
					}
				} else if (((xPos == 2) && (yPos == 6)) && (canWalkBack == true)) {
					if ((heldPlankDouble == false) && (heldPlankSingle == false)) {
						btnArray[2][5].setIcon(img.getWater1());
						btnArray[2][4].setIcon(img.getWater1());
						canWalkBack = false;
						heldPlankDouble = true;
					}
				} else if (((xPos == 2) && (yPos == 6)) && (canWalkBack == false)) {
					if (heldPlankDouble == true) {
						btnArray[2][5].setIcon(img.getPlank1());
						btnArray[2][4].setIcon(img.getPlank1());
						canWalkBack = true;
						heldPlankDouble = false;
						canWalkLeft = true;
					}
				}
			}
			if (e.getSource() == btnArray[1][6])/**********/
			{
				if (((xPos == 2) && (yPos == 6)) && (canWalkFront == true)) {
					if ((heldPlankDouble == false) && (heldPlankSingle == false)) {
						btnArray[1][6].setIcon(img.getWater1());
						canWalkFront = false;
						heldPlankSingle = true;
					}
				} else if (((xPos == 2) && (yPos == 6)) && (canWalkFront == false)) {
					if (heldPlankSingle == true) {
						btnArray[1][6].setIcon(img.getPlank2());
						canWalkFront = true;
						heldPlankSingle = false;
					}
				}
			}
		}

		if (SwingUtilities.isLeftMouseButton(e)) {
			if (((xPos == 7) && (yPos == 6)) || ((xPos == 6) && (yPos == 6)) || ((xPos == 3) && (yPos == 6)) || ((xPos == 4) && (yPos == 6)) || ((xPos == 1) && (yPos == 6))
					|| ((xPos == 5) && (yPos == 5)) || ((xPos == 5) && (yPos == 4)) || ((xPos == 4) && (yPos == 3)) || ((xPos == 3) && (yPos == 3)) || ((xPos == 2) && (yPos == 4))
					|| ((xPos == 2) && (yPos == 5))) {
				isStandingOnLog = true;
			} else {
				isStandingOnLog = false;
			}
			if (e.getSource() == btnArray[7][6])/**********/
			{
				if (((xPos == 8) && (yPos == 6)) && (canWalkFront == true)) {
					btnArray[8][6].setIcon(img.getStump2());
					btnArray[7][6].setIcon(img.getPlankMan2());
					xPos = 7;
					yPos = 6;

					gameStart = true;
				}

				else if (((xPos == 6) && (yPos == 6)) && (isStandingOnLog == true)) {
					btnArray[6][6].setIcon(img.getPlank2());
					btnArray[7][6].setIcon(img.getPlankMan2());
					xPos = 7;
					yPos = 6;
				}
			}

			if (e.getSource() == btnArray[8][6])/**********/
			{
				if (((xPos == 7) && (yPos == 6)) && (isStandingOnLog == true)) {
					btnArray[8][6].setIcon(img.getStumpMan2());
					btnArray[7][6].setIcon(img.getPlank2());
					xPos = 8;
					yPos = 6;
				}
			}

			if (e.getSource() == btnArray[6][6])/**********/
			{
				if (((xPos == 7) && (yPos == 6)) && (isStandingOnLog == true)) {
					btnArray[6][6].setIcon(img.getPlankMan2());
					btnArray[7][6].setIcon(img.getPlank2());
					xPos = 6;
					yPos = 6;
				}

				else if (((xPos == 5) && (yPos == 6)) && (canWalkBack == true)) {
					btnArray[6][6].setIcon(img.getPlankMan2());
					btnArray[5][6].setIcon(img.getStump1());
					xPos = 6;
					yPos = 6;
				}
			}

			if (e.getSource() == btnArray[5][6])/**********/
			{
				if (((xPos == 6) && (yPos == 6)) && (isStandingOnLog == true)) {
					btnArray[6][6].setIcon(img.getPlank2());
					btnArray[5][6].setIcon(img.getStumpMan1());
					xPos = 5;
					yPos = 6;
				}

				else if (((xPos == 4) && (yPos == 6)) && (isStandingOnLog == true)) {
					btnArray[4][6].setIcon(img.getPlank2());
					btnArray[5][6].setIcon(img.getStumpMan1());
					xPos = 5;
					yPos = 6;
					canWalkBack = false;
				}
			}
			if (e.getSource() == btnArray[5][5])/**********/
			{
				if (((xPos == 5) && (yPos == 6)) && (canWalkFront == true) && (firstEmptyEncounter2 == false)) {
					btnArray[5][5].setIcon(img.getPlankMan1());
					btnArray[5][6].setIcon(img.getStump1());
					xPos = 5;
					yPos = 5;
				}

				else if (((xPos == 5) && (yPos == 4)) && (isStandingOnLog == true)) {
					btnArray[5][4].setIcon(img.getPlank1());
					btnArray[5][5].setIcon(img.getPlankMan1());
					xPos = 5;
					yPos = 5;
				}
			}

			if (e.getSource() == btnArray[5][4])/**********/
			{
				if (((xPos == 5) && (yPos == 3)) && (canWalkBack == true) && (firstEmptyEncounter2 == false)) {
					btnArray[5][4].setIcon(img.getPlankMan1());
					btnArray[5][3].setIcon(img.getStump1());
					xPos = 5;
					yPos = 4;
				}

				else if (((xPos == 5) && (yPos == 5)) && (isStandingOnLog == true)) {
					btnArray[5][5].setIcon(img.getPlank1());
					btnArray[5][4].setIcon(img.getPlankMan1());
					xPos = 5;
					yPos = 4;
				}
			}

			if (e.getSource() == btnArray[5][3])/**********/
			{
				if (((xPos == 5) && (yPos == 4)) && (isStandingOnLog == true)) {
					btnArray[5][3].setIcon(img.getStumpMan1());
					btnArray[5][4].setIcon(img.getPlank1());
					xPos = 5;
					yPos = 3;
				}

				else if (((xPos == 4) && (yPos == 3)) && (isStandingOnLog == true)) {
					btnArray[5][3].setIcon(img.getStumpMan1());
					btnArray[4][3].setIcon(img.getPlank2());
					xPos = 5;
					yPos = 3;
				}
			}

			if (e.getSource() == btnArray[4][3])/**********/
			{
				if (((xPos == 5) && (yPos == 3)) && (canWalkFront == true) && (firstEmptyEncounter3 == false)) {
					btnArray[5][3].setIcon(img.getStump1());
					btnArray[4][3].setIcon(img.getPlankMan2());
					xPos = 4;
					yPos = 3;
				}

				else if (((xPos == 3) && (yPos == 3)) && (isStandingOnLog == true)) {
					btnArray[3][3].setIcon(img.getPlank2());
					btnArray[4][3].setIcon(img.getPlankMan2());
					xPos = 4;
					yPos = 3;
				}
			}

			if (e.getSource() == btnArray[3][3])/**********/
			{
				if (((xPos == 4) && (yPos == 3)) && (isStandingOnLog == true)) {
					btnArray[3][3].setIcon(img.getPlankMan2());
					btnArray[4][3].setIcon(img.getPlank2());
					xPos = 3;
					yPos = 3;
				}

				else if (((xPos == 2) && (yPos == 3)) && (canWalkBack == true)) {
					btnArray[2][3].setIcon(img.getStump1());
					btnArray[3][3].setIcon(img.getPlankMan2());
					xPos = 3;
					yPos = 3;
				}
			}

			if (e.getSource() == btnArray[2][3])/**********/
			{
				if (((xPos == 3) && (yPos == 3)) && (isStandingOnLog == true)) {
					btnArray[2][3].setIcon(img.getStumpMan1());
					btnArray[3][3].setIcon(img.getPlank2());
					xPos = 2;
					yPos = 3;
				}

				else if (((xPos == 2) && (yPos == 4)) && (isStandingOnLog == true)) {
					btnArray[2][3].setIcon(img.getStumpMan1());
					btnArray[2][4].setIcon(img.getPlank1());
					xPos = 2;
					yPos = 3;
				}
			}

			if (e.getSource() == btnArray[2][4])/**********/
			{
				if (((xPos == 2) && (yPos == 3)) && (canWalkFront == true) && (firstEmptyEncounter4 == false)) {
					btnArray[2][3].setIcon(img.getStump1());
					btnArray[2][4].setIcon(img.getPlankMan1());
					xPos = 2;
					yPos = 4;
				}

				else if (((xPos == 2) && (yPos == 5)) && (isStandingOnLog == true)) {
					btnArray[2][4].setIcon(img.getPlankMan1());
					btnArray[2][5].setIcon(img.getPlank1());
					xPos = 2;
					yPos = 4;
				}
			}
			if (e.getSource() == btnArray[2][5])/**********/
			{
				if (((xPos == 2) && (yPos == 4)) && (isStandingOnLog == true)) {
					btnArray[2][5].setIcon(img.getPlankMan1());
					btnArray[2][4].setIcon(img.getPlank1());
					xPos = 2;
					yPos = 5;
				}

				else if (((xPos == 2) && (yPos == 6)) && (canWalkLeft == true)) {
					btnArray[2][5].setIcon(img.getPlankMan1());
					btnArray[2][6].setIcon(img.getStump1());
					xPos = 2;
					yPos = 5;
				}
			}

			if (e.getSource() == btnArray[4][6])/**********/
			{
				if (((xPos == 5) && (yPos == 6)) && (canWalkFront == true) && (firstEmptyEncounter == false)) {
					btnArray[4][6].setIcon(img.getPlankMan2());
					btnArray[5][6].setIcon(img.getStump1());
					xPos = 4;
					yPos = 6;
				}

				else if (((xPos == 3) && (yPos == 6)) && (isStandingOnLog == true)) {
					btnArray[3][6].setIcon(img.getPlank2());
					btnArray[4][6].setIcon(img.getPlankMan2());
					xPos = 4;
					yPos = 6;
				}
			}

			if (e.getSource() == btnArray[3][6])/**********/
			{
				if (((xPos == 4) && (yPos == 6)) && (isStandingOnLog == true)) {
					btnArray[4][6].setIcon(img.getPlank2());
					btnArray[3][6].setIcon(img.getPlankMan2());
					xPos = 3;
					yPos = 6;
				}

				else if (((xPos == 2) && (yPos == 6)) && (canWalkBack == true)) {
					btnArray[2][6].setIcon(img.getStump1());
					btnArray[3][6].setIcon(img.getPlankMan2());
					xPos = 3;
					yPos = 6;
				}
			}

			if (e.getSource() == btnArray[2][6])/**********/
			{
				if (((xPos == 3) && (yPos == 6)) && (isStandingOnLog == true)) {
					btnArray[3][6].setIcon(img.getPlank2());
					btnArray[2][6].setIcon(img.getStumpMan1());
					xPos = 2;
					yPos = 6;

					canWalkBack = true;
				}

				else if (((xPos == 1) && (yPos == 6)) && (isStandingOnLog == true)) {
					btnArray[1][6].setIcon(img.getPlank2());
					btnArray[2][6].setIcon(img.getStumpMan1());
					xPos = 2;
					yPos = 6;
				} else if (((xPos == 2) && (yPos == 5)) && (isStandingOnLog == true)) {
					btnArray[2][5].setIcon(img.getPlank1());
					btnArray[2][6].setIcon(img.getStumpMan1());
					xPos = 2;
					yPos = 6;
				}
			}

			if (e.getSource() == btnArray[1][6])/**********/
			{
				if (((xPos == 2) && (yPos == 6)) && (canWalkFront == true)) {
					btnArray[2][6].setIcon(img.getStump1());
					btnArray[1][6].setIcon(img.getPlankMan2());
					xPos = 1;
					yPos = 6;
				}
			}

			if (e.getSource() == btnArray[0][6])/**********/
			{
				if (((xPos == 1) && (yPos == 6)) && (isStandingOnLog == true)) {
					btnArray[1][6].setIcon(img.getPlank2());
					btnArray[0][6].setIcon(img.getStumpMan3());
					xPos = 0;
					yPos = 6;

					gameStart = false;
				}
			}

		}
	}

	/**
	 * Determines if Level 2 has started or not
	 * 
	 * @return true if user is playing level 2 and false when the user has
	 *         fininshed level 2 or hasn't started it yet
	 */
	public boolean initiatelvl2() {
		return gameStart;
	}

	/**
	 * This just creates a 2D array of JButtons -It does not add buttons on the
	 * panel, it just creates them-
	 */
	public void addButtons() {
		for (int x = 0; x < ROWS; x++) {
			for (int y = 0; y < COLS; y++) {
				btnArray[x][y] = new JButton();
			}
		}
	}

	/**
	 * This adds all the JButtons in the btnArray array, on the panel and it
	 * also adds pictures on all the buttons. It creates a GUI, where planks,
	 * stumps and the player are at a valid position. It also adds the panel on
	 * the window which is used to display level 2
	 */
	public void startLevel2() {
		xPos = 8;
		yPos = 6;
		canWalkFront = true;
		canWalkBack = true;
		canWalkLeft = false;

		JFrame.setDefaultLookAndFeelDecorated(true);
		window.setTitle("Puzzle Week25_SCC110 - Level2");
		window.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		window.setContentPane(panel);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLayout(new GridLayout(ROWS, COLS));

		for (int x = 0; x < 1; x++) {
			if (x != 0)
				break;
			for (int y = 0; y < COLS; y++) {
				if (y == 6) {
					btnArray[x][y].setIcon(img.getStump3());
				} else {
					btnArray[x][y].setIcon(img.getBank2());
				}

				panel.add(btnArray[x][y]);
			}
		}

		for (int x = 1; x < ROWS; x++) {
			for (int y = 0; y < COLS; y++) {
				if (((x == 2) && (y == 6)) || ((x == 5) && (y == 6)) || ((x == 5) && (y == 3)) || ((x == 2) && (y == 3))) {
					btnArray[x][y].setIcon(img.getStump1());
				}

				else if (((x == 7) && (y == 6)) || ((x == 6) && (y == 6)) || ((x == 1) && (y == 6))) {
					btnArray[x][y].setIcon(img.getPlank2());
				}

				else if (((x == 6) && (y == 8)) || ((x == 4) && (y == 7)) || ((x == 1) && (y == 11)) || ((x == 4) && (y == 10))) {
					btnArray[x][y].setIcon(img.getWater2());
				}

				else if (((x == 5) && (y == 0)) || ((x == 6) && (y == 1)) || ((x == 2) && (y == 11)) || ((x == 4) && (y == 10))) {
					btnArray[x][y].setIcon(img.getWater3());
				}

				else if (((x == 2) && (y == 5)) || ((x == 1) && (y == 7))) {
					btnArray[x][y].setIcon(img.getWater4());
				}

				else {
					btnArray[x][y].setIcon(img.getWater1());
				}
				panel.add(btnArray[x][y]);
			}
		}

		for (int x = 8; x < ROWS; x++) {
			if (x != 8)
				break;
			for (int y = 0; y < COLS; y++) {
				if (y == 6) {
					btnArray[x][y].setIcon(img.getStumpMan2());
				} else {
					btnArray[x][y].setIcon(img.getBank1());
				}

				panel.add(btnArray[x][y]);
			}
		}
	}

}