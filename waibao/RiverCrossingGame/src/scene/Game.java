package scene;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import javafx.embed.swing.JFXPanel;
import main.StartGame;
import scene.AllUnitInGame.SingleUnit;
import scene.ScorePlan.PlayScore;
import scene.position.AllSceneInitPosition;
import scene.position.Position;
import unit.ReadGameModelUnit;

/**
 * 游戏逻辑类 负责加载不同难度的游戏界面，人物移动，捡起木板，完成游戏，游戏逻辑的类。
 * 该类就是一个鼠标点击事件类。有专门的方法用来处理鼠标被点击后会执行什么操作。 <strong>游戏设计逻辑:</strong>
 * <strong>游戏面板是由一个一个的像素点组成的
 * （每个像素点就是一张图片），并且每个像素点都是一个按钮（可以通过拉伸该界面就可以发现），并且通过ImageIcon i = new
 * ImageIcon(“bank1.jpg”); JButton b = new JButton(i);的模式将指定的图片放到按钮上。
 * .当玩家点击窗口中任意一个点时，都能立马被感知哪个点被点击，从而做出不同的处理（左击或右击）</strong>
 */
public class Game extends MouseAdapter {
	// 玩家所在位置(x,y)
	private Position nowPosition;
	// 本轮游戏玩家的目的地位置
	private Position destination;

	// 玩家当前手上所持的木板长度（为0 或null时表示玩家没有捡木板）
	private Integer pickPlankLength;

	// 游戏面板所有的游戏单元，里面包含每个像素的按钮、位置、图片
	private AllUnitInGame allUnitInGame;

	// 游戏界面
	private JFrame gameWindow = new JFrame();
	private JFXPanel jfxPanel = new JFXPanel();

	// 计时器面板
	private TimeScene useTimePlane = new TimeScene();

	// 本次游戏开始时间
	private long startTime;
	// 玩家已花费的时间
	private int hadUseTime;

	// 本次游戏的难度
	private String thisGameLevel;

	public Game(String level) {
		jfxPanel.setPreferredSize(new java.awt.Dimension(13 * 50, 9 * 50));
		String allUnitStr = getAllUnitStrByLevel(level);
		allUnitInGame = AllUnitInGame.parse(allUnitStr);
		nowPosition = allUnitInGame.startPosition;
		destination = allUnitInGame.winPosition;
		for (SingleUnit unit : allUnitInGame.getAllSingleUnit()) {
			jfxPanel.add(unit.button);
		}

		thisGameLevel = level;
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

		// 开启一个新的线程，用于刷新计时面板
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
		// 添加鼠标监听事件（左击表示要行走，右击表示捡起木板）
		mouseListener();
	}

	private static String getAllUnitStrByLevel(String level) {
		return ReadGameModelUnit.readToString(level);
	}

	// 添加鼠标监听事件
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

	// 当确定玩家到达目的地后，执行的通关操作
	private void win() {
		hadUseTime = (int) ((System.currentTimeMillis() - startTime) / 1000);

		// 添加该玩家到排行版中（如果上不了排行版就不会改变任何东西）
		PlayScore playScore = new PlayScore(StartGame.player.getPlayerName(), thisGameLevel, hadUseTime);
		ScorePlan scorePlan = StartGame.mainScene.getScorePlan();
		scorePlan.addPlayScore(playScore);

		// 销毁游戏面板和记分面板
		gameWindow.dispose();
		useTimePlane.dispose();

		// 判断下一关的名字，并弹出恭喜面板，并结束该次游戏逻辑
		String nextLevel = getNextLevel();
		new CongratulationScene(thisGameLevel, nextLevel == null, 10);
		StartGame.player.endGame(nextLevel);
	}

	private String getNextLevel() {
		if (thisGameLevel.equals("Level1"))
			return "Level2";
		if (thisGameLevel.equals("Level2"))
			return "Level3";
		return null;
	}

	/**
	 * 玩家点击左键，移动到指定位置
	 */
	private void move(SingleUnit singleUnit) {
		List<Position> path = movefindPath(singleUnit.position);
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
				afterUnit.setNewImageIcon("plank1_man");
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
		if (movefindPath(singleUnit.position).size() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 玩家点击指定位置，打算移动到指定位置。找出从玩家当前位置移动到指定位置的合法路径（如果没有就返回空集合）
	 */
	private List<Position> movefindPath(Position destinationPosition) {
		List<Position> resList = new ArrayList<Position>();
		// 玩家不能斜着走
		if (destinationPosition.x != nowPosition.x && destinationPosition.y != nowPosition.y) {
			return resList;
		}
		if (destinationPosition.x == nowPosition.x) {
			// 当玩家点击的目的地点和当前位置是同一竖直方向
			return sameXFindPath(destinationPosition.y);
		}
		if (destinationPosition.y == nowPosition.y) {
			// 当玩家点击的目的地点和当前位置是同一水平方向
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
		// 当玩家点击的目的地点在当前位置的下方
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
			// 当玩家点击的目的地点在当前位置的上方
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

	// 玩家右击，准备放下木板
	private void putDownPlank(Position clickPosition) {
		List<Position> pickDownPositions = getPutDownPosition(clickPosition);
		if (pickDownPositions.size() > 0) {
			if (isSameX(clickPosition)) {
				for (Position position : pickDownPositions) {
					allUnitInGame.getUnitByPosition(position).setNewImageIcon("plank2");
				}
			} else {
				for (Position position : pickDownPositions) {
					allUnitInGame.getUnitByPosition(position).setNewImageIcon("plank1");
				}
			}
			pickPlankLength = null;
		}
	}

	private boolean isSameX(Position clickPosition) {
		return nowPosition.x == clickPosition.x;
	}

	private List<Position> getPutDownPosition(Position clickPosition) {
		List<Position> resList = new ArrayList<Position>();
		if (nowPosition.x != clickPosition.x && nowPosition.y != clickPosition.y) {
			return resList;
		}
		if (nowPosition.x == clickPosition.x && nowPosition.y == clickPosition.y) {
			return resList;
		}
		if (!isPlankLengthIsSame(clickPosition)) {
			return resList;
		}
		if (nowPosition.x == clickPosition.x) {
			return getAllCanPickDownPositionSameX(clickPosition);
		}
		// 木板横放的情况
		if (nowPosition.y == clickPosition.y) {
			return getAllCanPickDownPositionSameY(clickPosition);
		}
		return resList;
	}

	private boolean isPlankLengthIsSame(Position clickPosition) {
		int needLength = 0;
		if (nowPosition.y > clickPosition.y) {
			boolean isBi = true;
			for (int i = nowPosition.y - 1; i >= clickPosition.y; i--) {
				if (allUnitInGame.getUnitByPosition(new Position(nowPosition.x, i)).imageName.startsWith("water")) {
					needLength++;
				} else {
					isBi = false;
					break;
				}
			}
			if (isBi) {
				for (int i = clickPosition.y - 1; i >= 0; i--) {
					if (allUnitInGame.getUnitByPosition(new Position(nowPosition.x, i)).imageName.startsWith("water")) {
						needLength++;
					} else {
						break;
					}
				}
			}
		} else {
			boolean isBi = true;
			for (int i = nowPosition.y + 1; i <= clickPosition.y; i++) {
				if (allUnitInGame.getUnitByPosition(new Position(nowPosition.x, i)).imageName.startsWith("water")) {
					needLength++;
				} else {
					isBi = false;
					break;
				}
			}
			if (isBi) {
				for (int i = clickPosition.y + 1; i <= 8; i++) {
					if (allUnitInGame.getUnitByPosition(new Position(nowPosition.x, i)).imageName.startsWith("water")) {
						needLength++;
					} else {
						break;
					}
				}
			}
		}
		return needLength == pickPlankLength;
	}

	// // 玩家右击，准备捡起木板木板，返回所有能捡起的木板的位置集合（没有就返回空集合）同一竖直方向点击
	private List<Position> getAllCanPickDownPositionSameX(Position clickPosition) {
		List<Position> resList = new ArrayList<Position>();
		if (nowPosition.y > clickPosition.y) {
			// 点击位置在当前位置的上方
			boolean isBi = true;
			for (int i = nowPosition.y - 1; i >= clickPosition.y; i--) {
				if (allUnitInGame.getUnitByPosition(new Position(nowPosition.x, i)).imageName.startsWith("water")) {
					resList.add(new Position(nowPosition.x, i));
				} else {
					isBi = false;
					break;
				}
			}
			if (isBi) {
				for (int i = clickPosition.y - 1; i >= 0; i--) {
					if (allUnitInGame.getUnitByPosition(new Position(nowPosition.x, i)).imageName.startsWith("water")) {
						resList.add(new Position(nowPosition.x, i));
					} else {
						break;
					}
				}
			}
		} else {
			// 点击位置在当前位置的下方
			boolean isBi = true;
			for (int i = nowPosition.y + 1; i <= clickPosition.y; i++) {
				if (allUnitInGame.getUnitByPosition(new Position(nowPosition.x, i)).imageName.startsWith("water")) {
					resList.add(new Position(nowPosition.x, i));
				} else {
					isBi = false;
					break;
				}
			}
			if (isBi) {
				for (int i = clickPosition.y + 1; i <= 8; i++) {
					if (allUnitInGame.getUnitByPosition(new Position(nowPosition.x, i)).imageName.startsWith("water")) {
						resList.add(new Position(nowPosition.x, i));
					} else {
						break;
					}
				}
			}
		}
		return resList;
	}

	// // 玩家右击，准备捡起木板木板，返回所有能捡起的木板的位置集合（没有就返回空集合）同一水平方向点击
	private List<Position> getAllCanPickDownPositionSameY(Position clickPosition) {
		List<Position> resList = new ArrayList<Position>();
		if (nowPosition.x > clickPosition.x) {
			// 点击位置在当前位置的左方
			boolean isBi = true;
			for (int i = clickPosition.x; i < nowPosition.x; i++) {
				if (allUnitInGame.getUnitByPosition(new Position(i, nowPosition.y)).imageName.startsWith("water")) {
					resList.add(new Position(i, nowPosition.y));
				} else {
					isBi = false;
					break;
				}
			}
			if (isBi) {
				for (int i = clickPosition.x - 1; i >= 0; i--) {
					if (allUnitInGame.getUnitByPosition(new Position(i, nowPosition.y)).imageName.startsWith("water")) {
						resList.add(new Position(i, nowPosition.y));
					} else {
						break;
					}
				}
			}
		} else {
			// 点击位置在当前位置的右方
			boolean isBi = true;
			for (int i = nowPosition.x + 1; i >= clickPosition.x; i++) {
				if (allUnitInGame.getUnitByPosition(new Position(i, nowPosition.y)).imageName.startsWith("water")) {
					resList.add(new Position(i, nowPosition.y));
				} else {
					isBi = false;
					break;
				}
			}
			if (isBi) {
				for (int i = clickPosition.y + 1; i >= 12; i++) {
					if (allUnitInGame.getUnitByPosition(new Position(i, nowPosition.y)).imageName.startsWith("water")) {
						resList.add(new Position(i, nowPosition.y));
					} else {
						break;
					}
				}
			}
		}
		if (resList.size() != pickPlankLength) {
			resList = new ArrayList<Position>();
		}
		return resList;
	}

	// 玩家捡起指定位置的木板，将按钮上的木板图片转为水的图片
	private void pickUpPlank(Position clickPosition) {
		List<Position> pickUpPosition = getPickUpPlankPosition(clickPosition);
		if (pickUpPosition.size() > 0) {
			for (Position position : pickUpPosition) {
				SingleUnit unit = allUnitInGame.getUnitByPosition(position);
				unit.setNewImageIcon("water1");
			}
			pickPlankLength = pickUpPosition.size();
		}
	}

	/**
	 * 获取所有点击处，所有能捡起的木板的位置（Position(x,y)）
	 */
	private List<Position> getPickUpPlankPosition(Position clickPosition) {
		List<Position> resList = new ArrayList<Position>();
		if (nowPosition.x != clickPosition.x && nowPosition.y != clickPosition.y) {
			return resList;
		}
		if (nowPosition.x == clickPosition.x && nowPosition.y == clickPosition.y) {
			return resList;
		}
		if (nowPosition.x == clickPosition.x) {
			return getAllCanPickUpPositionSameX(clickPosition);
		}

		// 木板横放的情况
		if (nowPosition.y == clickPosition.y) {
			return getAllCanPickUpPositionSameY(clickPosition);
		}
		return resList;
	}

	/**
	 * 木板横放的情况,判断玩家是否能捡起木板，并返回所有能捡起木板的position
	 */
	private List<Position> getAllCanPickUpPositionSameY(Position clickPosition) {
		List<Position> resList = new ArrayList<Position>();
		if (nowPosition.x > clickPosition.x) {
			boolean isBigClickPosHavePlank = true;
			for (int i = nowPosition.x - 1; i <= clickPosition.x; i--) {
				SingleUnit unit = allUnitInGame.getUnitByPosition(new Position(i, nowPosition.y));
				if (unit.imageName.equals("plank1")) {
					resList.add(new Position(i, nowPosition.y));
				} else {
					isBigClickPosHavePlank = false;
					break;
				}
			}
			if (isBigClickPosHavePlank) {
				for (int i = clickPosition.x - 1; i >= 0; i--) {
					SingleUnit unit = allUnitInGame.getUnitByPosition(new Position(i, nowPosition.y));
					if (unit.imageName.equals("plank1")) {
						resList.add(new Position(i, nowPosition.y));
					} else {
						break;
					}
				}
			}
		} else {
			boolean isBigClickPosHavePlank = true;
			for (int i = nowPosition.x + 1; i <= clickPosition.x; i++) {
				SingleUnit unit = allUnitInGame.getUnitByPosition(new Position(i, nowPosition.y));
				if (unit.imageName.equals("plank1")) {
					resList.add(new Position(i, nowPosition.y));
				} else {
					isBigClickPosHavePlank = false;
					break;
				}
			}
			if (isBigClickPosHavePlank) {
				for (int i = clickPosition.x + 1; i <= 12; i++) {
					SingleUnit unit = allUnitInGame.getUnitByPosition(new Position(i, nowPosition.y));
					if (unit.imageName.equals("plank1")) {
						resList.add(new Position(i, nowPosition.y));
					} else {
						break;
					}
				}
			}
		}
		return resList;
	}

	/**
	 * 木板竖放的情况,判断玩家是否能捡起木板，并返回所有能捡起木板的position
	 */
	private List<Position> getAllCanPickUpPositionSameX(Position clickPosition) {
		List<Position> resList = new ArrayList<Position>();
		if (nowPosition.y > clickPosition.y) {
			boolean isBigClickPosHavePlank = true;
			for (int i = nowPosition.y - 1; i >= clickPosition.y; i--) {
				SingleUnit unit = allUnitInGame.getUnitByPosition(new Position(nowPosition.x, i));
				if (unit.imageName.equals("plank2")) {
					resList.add(new Position(nowPosition.x, i));
				} else {
					isBigClickPosHavePlank = false;
					break;
				}
			}
			if (isBigClickPosHavePlank) {
				for (int i = clickPosition.y - 1; i >= 0; i--) {
					SingleUnit unit = allUnitInGame.getUnitByPosition(new Position(nowPosition.x, i));
					if (unit.imageName.equals("plank2")) {
						resList.add(new Position(nowPosition.x, i));
					} else {
						break;
					}
				}
			}
		} else {
			boolean isBigClickPosHavePlank = true;
			for (int i = nowPosition.y + 1; i <= clickPosition.y; i++) {
				SingleUnit unit = allUnitInGame.getUnitByPosition(new Position(nowPosition.x, i));
				if (unit.imageName.equals("plank2")) {
					resList.add(new Position(nowPosition.x, i));
				} else {
					isBigClickPosHavePlank = false;
					break;
				}
			}
			if (isBigClickPosHavePlank) {
				for (int i = clickPosition.y + 1; i <= 8; i++) {
					SingleUnit unit = allUnitInGame.getUnitByPosition(new Position(nowPosition.x, i));
					if (unit.imageName.equals("plank2")) {
						resList.add(new Position(nowPosition.x, i));
					} else {
						break;
					}
				}
			}
		}
		return resList;
	}

	/**
	 * 是否胜利
	 */
	private boolean isWin() {
		return nowPosition.isSamePosition(destination);
	}
}
