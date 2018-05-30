package main;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Game extends MouseAdapter {
	private Point nowPoint = new Point(2, 12);
	private Point winPoint = new Point(6, 0);
	private int plankLengthInHand;
	private AllButtonObjectInGame allButtonObjectInGame = new AllButtonObjectInGame();
	private List<Point> legelPoint = new ArrayList<>();
	private JFrame gameWindow = new JFrame();
	private TimeScene timePlane = new TimeScene();
	private long startTime = System.currentTimeMillis();

	public static void main(String[] args) {
		new Game();
	}

	public Game() {
		gameWindow.setContentPane(allButtonObjectInGame);
		gameWindow.pack();
		gameWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				timePlane.dispose();
			}
		});

		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setVisible(true);
		gameWindow.setLayout(new GridLayout(13, 9));

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (;;) {
					int hadUseTime = (int) ((System.currentTimeMillis() - startTime) / 1000);
					timePlane.changeUseTime(hadUseTime);
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

	private void initIegalPoint() {
		legelPoint.add(new Point());
		legelPoint.add(new Point());
		legelPoint.add(new Point());
		legelPoint.add(new Point());
		legelPoint.add(new Point());
		legelPoint.add(new Point());
		legelPoint.add(new Point());
		legelPoint.add(new Point());
		legelPoint.add(new Point());
		legelPoint.add(new Point());
		legelPoint.add(new Point());
		legelPoint.add(new Point());
		legelPoint.add(new Point());
	}

	public void mouseListener() {
		for (List<ButtonObject> bos : allButtonObjectInGame.buttonObjects) {
			for (ButtonObject bo : bos) {
				bo.button.addMouseListener(this);
			}
		}
	}

	/**
	 * 监听鼠标点击事件（左击 —— 移动，右击 —— 拾起木板或放下木板）
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		Point clickPoint = allButtonObjectInGame.getPointByButton((JButton) e.getSource()).point;
		if (SwingUtilities.isRightMouseButton(e)) {
			if (plankLengthInHand == 0) {

			} else {

			}
		} else if (SwingUtilities.isLeftMouseButton(e)) {
			move(clickPoint);
		}

		if (nowPoint.equals(winPoint)) {
			win();
		}
	}

	private void putDownPlank(Point clickPoint) {
		if (!allButtonObjectInGame.getButtonObjectByPoint(clickPoint).imageName.startsWith("water")) {
			return;
		}
		String plankName = getPlankName(clickPoint);
		if (plankName == null)
			return;
		List<Point> targetPoints = getPlankPoints(clickPoint);
		for (Point point : targetPoints) {
			allButtonObjectInGame.changeButtonImage(point, plankName);
		}
		plankLengthInHand = 0;
	}

	private String getPlankName(Point clickPoint) {
		if (clickPoint.x == nowPoint.x)
			return "plank2";
		else if (clickPoint.y == nowPoint.y)
			return "plank1";
		return null;
	}

	private void pickUpPlank(Point clickPoint) {
		if (!allButtonObjectInGame.getButtonObjectByPoint(clickPoint).imageName.startsWith("plank")) {
			return;
		}
		List<Point> targetPoints = getPlankPoints(clickPoint);
		for (Point point : targetPoints) {
			allButtonObjectInGame.changeButtonImage(point, "water1");
		}
		plankLengthInHand = targetPoints.size();
	}

	private List<Point> getPlankPoints(Point clickPoint) {

		return null;
	}

	private void move(Point targetPoint) {
		if (canMove(targetPoint)) {
			String targetBeforeImageName = allButtonObjectInGame.getButtonObjectByPoint(targetPoint).imageName;
			String nowBeforeImageName = allButtonObjectInGame.getButtonObjectByPoint(nowPoint).imageName;
			allButtonObjectInGame.changeButtonImage(targetPoint, targetBeforeImageName + "_man");
			allButtonObjectInGame.changeButtonImage(nowPoint, nowBeforeImageName.substring(0, nowBeforeImageName.length() - 4));
			nowPoint = targetPoint;
		}
	}

	private boolean canMove(Point targetPoint) {
		if (targetPoint.equals(nowPoint)) {
			return false;
		}
		if (allButtonObjectInGame.getButtonObjectByPoint(targetPoint).imageName.startsWith("water")) {
			return false;
		}
		if (allButtonObjectInGame.getButtonObjectByPoint(targetPoint).imageName.startsWith("bank")) {
			return false;
		}
		if (targetPoint.x != nowPoint.x && targetPoint.y != targetPoint.y) {
			return false;
		}
		if (targetPoint.x != nowPoint.x) {
			for (int i = Integer.min(targetPoint.x, nowPoint.x); i <= Integer.max(targetPoint.x, nowPoint.x); i++) {
				if (!allButtonObjectInGame.getButtonObjectByPoint(new Point(i, nowPoint.y)).isPlayerCanStand()) {
					return false;
				}
			}
		} else {
			for (int i = Integer.min(targetPoint.y, nowPoint.y); i <= Integer.max(targetPoint.y, nowPoint.y); i++) {
				if (!allButtonObjectInGame.getButtonObjectByPoint(new Point(nowPoint.x, i)).isPlayerCanStand()) {
					return false;
				}
			}
		}
		return true;
	}

	private void win() {
		gameWindow.dispose();
		new WinScene((int) ((System.currentTimeMillis() - startTime) / 1000));
	}
}
