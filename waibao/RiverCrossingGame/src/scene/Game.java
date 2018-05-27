package scene;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import javafx.embed.swing.JFXPanel;
import scene.AllUnitInGame.SingleUnit;
import scene.position.Position;
import unit.ReadGameModelUnit;

public class Game extends MouseAdapter {
	private Position nowPosition;
	private Position destination;
	private Integer pickPlankLength;
	private AllUnitInGame allUnitInGame;

	private JFrame gameWindow = new JFrame();
	private JFXPanel jfxPanel = new JFXPanel();

	private long hadUseTime;

	public static void main(String[] args) {
		new Game("Level1");
	}

	public Game(String level) {
		jfxPanel.setPreferredSize(new java.awt.Dimension(13 * 50, 9 * 50));
		String allUnitStr = getAllUnitStrByLevel(level);
		allUnitInGame = AllUnitInGame.parse(allUnitStr);
		for (SingleUnit unit : allUnitInGame.getAllSingleUnit()) {
			jfxPanel.add(unit.button);
		}

		gameWindow.setContentPane(jfxPanel);
		gameWindow.pack();
		gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gameWindow.setVisible(true);
		gameWindow.setLayout(new GridLayout(9, 13));
		mouseListener();
	}

	private static String getAllUnitStrByLevel(String level) {
		return ReadGameModelUnit.readToString(level);
	}

	public void mouseListener() {
		for (SingleUnit unit : allUnitInGame.getAllSingleUnit()) {
			unit.button.addMouseListener(this);
		}
	}

	/**
	 * 监听鼠标点击事件（左击——移动，右击——拾起木板或放下木板）
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			System.out.println("s");
			move(e);
		} else if (SwingUtilities.isLeftMouseButton(e)) {
			System.out.println("s");
			if (pickPlankLength == null) {
				// pickUpPlank();
			} else {
				// putDownPlank();
			}
		}
	}

	/**
	 * 移动
	 */
	private void move(MouseEvent e) {
		if (isCanMove(new Position(0, 0))) {
			return;
		}
	}

	private boolean isCanMove(Position nextPosition) {
		return false;
	}

	private void putDownPlank(Position clickPosition) {
		if (!isCanPutDownPlank()) {
			return;
		}
	}

	private boolean isCanPutDownPlank() {
		return false;
	}

	private void pickUpPlank(Position clickPosition) {
		if (!isCanPickUpPlank()) {
			return;
		}
	}

	private boolean isCanPickUpPlank() {
		return false;
	}

	/**
	 * 是否胜利
	 */
	private boolean isWin() {
		return nowPosition.isSamePosition(destination);
	}
}
