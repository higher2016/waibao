package scene;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javafx.embed.swing.JFXPanel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import main.StartGame;
import scene.AllUnitInGame.SingleUnit;
import scene.ScorePlan.PlayScore;
import scene.position.AllSceneInitPosition;
import scene.position.Position;
import unit.ReadGameModelUnit;

public class Game extends MouseAdapter {
	private Position nowPosition;
	private Position destination;
	private Integer pickPlankLength;
	private AllUnitInGame allUnitInGame;

	private JFrame gameWindow = new JFrame();
	private JFXPanel jfxPanel = new JFXPanel();
	private TimeScene useTimePlane = new TimeScene();

	private long startTime;
	private int hadUseTime;

	public static void main(String[] args) {
		new Game("Level1");
	}

	public Game(String level) {
		jfxPanel.setPreferredSize(new java.awt.Dimension(13 * 50, 9 * 50));
		String allUnitStr = getAllUnitStrByLevel(level);
		allUnitInGame = AllUnitInGame.parse(allUnitStr);
		nowPosition = allUnitInGame.startPosition;
		destination = allUnitInGame.winPosition;
		for (SingleUnit unit : allUnitInGame.getAllSingleUnit()) {
			jfxPanel.add(unit.button);
		}

		gameWindow.setContentPane(jfxPanel);
		gameWindow.pack();
		gameWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				useTimePlane.dispose();
			}
		});
		gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gameWindow.setVisible(true);
		gameWindow.setLocation(AllSceneInitPosition.Game.x, AllSceneInitPosition.Game.y);
		gameWindow.setLayout(new GridLayout(9, 13));

		startTime = System.currentTimeMillis();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (;;) {
					hadUseTime = (int) ((System.currentTimeMillis() - startTime) / 1000);
					useTimePlane.changeUseTime(hadUseTime);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
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
	 * 监听鼠标点击事件（左击 —— 移动，右击 —— 拾起木板或放下木板）
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		SingleUnit singleUnit = allUnitInGame.getSingleUnit((JButton) e.getSource());
		System.out.println("x:" + singleUnit.position.x + ", y:" + singleUnit.position.y);
		if (SwingUtilities.isRightMouseButton(e)) {
			if (pickPlankLength == null || pickPlankLength == 0) {
				pickUpPlank(singleUnit.position);
			} else {
				putDownPlank(singleUnit.position);
			}
		} else if (SwingUtilities.isLeftMouseButton(e) && isCanMove(singleUnit)) {
			move(singleUnit);
		}

		if (isWin()) {
			win();
		}
	}

	private void win() {
		hadUseTime = (int) ((System.currentTimeMillis() - startTime) / 1000);
		PlayScore playScore = new PlayScore(StartGame.player.getPlayerName(), StartGame.player.getThisGameLevel(), hadUseTime);
		StartGame.mainScene.getScorePlan().addPlayScore(playScore );
		
		gameWindow.dispose();
		new CongratulationScene("Level2", false, 10);
		StartGame.player.endGame();
	}

	/**
	 * 移动
	 */
	private void move(SingleUnit singleUnit) {
		List<Position> path = findPath(singleUnit.position);
		for (Position position : path) {
			SingleUnit nowUnit = allUnitInGame.getUnitByPosition(nowPosition);
			if (nowUnit.getImageName().equals("plank1_man")) {
				nowUnit.setNewImageIcon("plank1");
			}
			if (nowUnit.getImageName().equals("plank2_man")) {
				nowUnit.setNewImageIcon("plank2");
			}
			if (nowUnit.getImageName().equals("stump1_man")) {
				nowUnit.setNewImageIcon("stump1");
			}
			if (nowUnit.getImageName().equals("stump2_man")) {
				nowUnit.setNewImageIcon("stump2");
			}
			if (nowUnit.getImageName().equals("stump3_man")) {
				nowUnit.setNewImageIcon("stump3");
			}

			SingleUnit afterUnit = allUnitInGame.getUnitByPosition(position);
			if (afterUnit.getImageName().equals("plank1")) {
				afterUnit.setNewImageIcon("pank1_man");
			}
			if (afterUnit.getImageName().equals("plank2")) {
				afterUnit.setNewImageIcon("plank2_man");
			}
			if (afterUnit.getImageName().equals("stump1")) {
				afterUnit.setNewImageIcon("stump1_man");
			}
			if (afterUnit.getImageName().equals("stump2")) {
				afterUnit.setNewImageIcon("stump2_man");
			}
			if (afterUnit.getImageName().equals("stump3")) {
				afterUnit.setNewImageIcon("stump3_man");
			}
			nowPosition = afterUnit.position;
		}
	}

	private boolean isCanMove(SingleUnit singleUnit) {
		if (singleUnit.imageName.startsWith("bank") || singleUnit.imageName.startsWith("water")) {
			return false;
		}
		if (singleUnit.position.isSamePosition(nowPosition)) {
			return false;
		}
		if (findPath(singleUnit.position).size() == 0) {
			return false;
		}
		return true;
	}

	private List<Position> findPath(Position destinationPosition) {
		List<Position> resList = new ArrayList<Position>();
		if (destinationPosition.x != nowPosition.x && destinationPosition.y != nowPosition.y) {
			return resList;
		}
		if (destinationPosition.x == nowPosition.x) {
			return sameXFindPath(destinationPosition.y);
		}
		if (destinationPosition.y == nowPosition.y) {
			return sameYFindPath(destinationPosition.x);
		}
		return resList;
	}

	private List<Position> sameYFindPath(int desX) {
		List<Position> resList = new ArrayList<Position>();
		if (desX > nowPosition.x) {
			for (int i = nowPosition.x + 1; i <= desX; i++) {
				Position position = new Position(i, nowPosition.y);
				String thisPostionImageName = allUnitInGame.getUnitByPosition(position).imageName;
				if (thisPostionImageName.startsWith("plank") || thisPostionImageName.startsWith("stump")) {
					resList.add(position);
				} else {
					return new ArrayList<>();
				}
			}
		} else if (desX < nowPosition.x) {
			for (int i = nowPosition.x - 1; i >= desX; i--) {
				Position position = new Position(i, nowPosition.y);
				String thisPostionImageName = allUnitInGame.getUnitByPosition(position).imageName;
				if (thisPostionImageName.startsWith("plank") || thisPostionImageName.startsWith("stump")) {
					resList.add(position);
				} else {
					return new ArrayList<>();
				}
			}
		}
		return resList;
	}

	private List<Position> sameXFindPath(int desY) {
		List<Position> resList = new ArrayList<Position>();
		if (desY > nowPosition.y) {
			for (int i = nowPosition.y + 1; i <= desY; i++) {
				Position position = new Position(nowPosition.x, i);
				String thisPostionImageName = allUnitInGame.getUnitByPosition(position).imageName;
				if (thisPostionImageName.startsWith("plank") || thisPostionImageName.startsWith("stump")) {
					resList.add(position);
				} else {
					return new ArrayList<>();
				}
			}
		} else if (desY < nowPosition.y) {
			for (int i = nowPosition.y - 1; i >= desY; i--) {
				Position position = new Position(nowPosition.x, i);
				String thisPostionImageName = allUnitInGame.getUnitByPosition(position).imageName;
				if (thisPostionImageName.startsWith("plank") || thisPostionImageName.startsWith("stump")) {
					resList.add(position);
				} else {
					return new ArrayList<>();
				}
			}
		}
		return resList;
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
