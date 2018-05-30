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

/**
 * 游戏界面类的设计原则是：每一张原始图片都作为界面中的一个不可分像素（ButtonObject）点，每个像素点又是一个按钮（只不过将图片放在上面）.所有的像素点就是AllButtonObjectInGame对象
 * 这样玩家在点击任意一个像素点的时候，程序就可以立马监听到哪一个位置的按钮被点击（左击还是右击），从而做出相应的处理（移动、捡起木板或是放下木板）
 */
public class Game extends MouseAdapter {
	// 当前手上拿着的plank的长度
	private int plankLengthInHand;
	private AllButtonObjectInGame allButtonObjectInGame = new AllButtonObjectInGame();
	private JFrame gameWindow = new JFrame(); // 游戏主界面
	// 计时页面
	private TimeScene timePlane = new TimeScene();
	// 游戏开始的时间，用于计时页面刷新用
	private long startTime = System.currentTimeMillis();
	private Point nowPoint = new Point(2, 12);
	private Point winPoint = new Point(6, 0);

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
		// 确定游戏界面的布局
		gameWindow.setLayout(new GridLayout(13, 9));

		// 开启线程用来做，计时页面的时间刷新
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (;;) {
					int hadUseTime = (int) ((System.currentTimeMillis() - startTime) / 1000);
					timePlane.changeUseTime(hadUseTime);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		mouseListener();
	}

	// 监听该页面所有按钮对象的按下操作
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
		// 确定玩家点击画面的位置(x，y)
		Point clickPoint = allButtonObjectInGame.getPointByButton((JButton) e.getSource()).point;
		if (clickPoint.equals(nowPoint)) {
			return;
		}
		// 右击鼠标，准备捡起或放下木板
		if (SwingUtilities.isRightMouseButton(e)) {
			// 手上没有木板就是准备捡起
			if (plankLengthInHand == 0) {
				pickUpPlank(clickPoint);
			} else {
				// 手上有木板就是准备放下
				putDownPlank(clickPoint);
			}
		} else if (SwingUtilities.isLeftMouseButton(e)) {
			move(clickPoint);
		}

		if (nowPoint.equals(winPoint)) {
			win();
		}
	}

	/**
	 * 放下木板操作
	 */
	private void putDownPlank(Point clickPoint) {
		// 如果选择放下的位置不是水上面则非法，不进行操作
		if (!allButtonObjectInGame.getButtonObjectByPoint(clickPoint).imageName.startsWith("water")) {
			return;
		}
		// 确定放下的木板是横向放还是纵向放
		String plankName = getPlankName(clickPoint);
		if (plankName == null)
			return;
		// 确定所有要放下木板的地方
		List<Point> targetPoints = getPlankPoints(clickPoint);
		// 如果需要的木板长度大于手上所持的木板长度，则放下失败
		if (targetPoints.size() != plankLengthInHand) {
			return;
		}
		// 将水的图片置换为木板图片
		for (Point point : targetPoints) {
			allButtonObjectInGame.changeButtonImage(point, plankName);
		}
		// 将手上木板长度减为0
		plankLengthInHand = 0;
	}

	private String getPlankName(Point clickPoint) {
		if (clickPoint.x == nowPoint.x)
			return "plank2";
		else if (clickPoint.y == nowPoint.y)
			return "plank1";
		return null;
	}

	/**
	 * 捡起木板操作
	 * 
	 * @param clickPoint
	 */
	private void pickUpPlank(Point clickPoint) {
		// 点击位置不是木板，不做操作
		if (!allButtonObjectInGame.getButtonObjectByPoint(clickPoint).imageName.startsWith("plank")) {
			return;
		}
		// 获取所有应该捡起的木板的位置
		List<Point> targetPoints = getPlankPoints(clickPoint);
		for (Point point : targetPoints) {
			allButtonObjectInGame.changeButtonImage(point, "water1");
		}
		// 修改现在手上所持木板的长度
		plankLengthInHand = targetPoints.size();
	}

	private List<Point> getPlankPoints(Point clickPoint) {
		if (clickPoint.x != nowPoint.x && clickPoint.y != nowPoint.y) {
			return new ArrayList<>();
		}
		if (!allButtonObjectInGame.getButtonObjectByPoint(nowPoint).imageName.startsWith("stump")) {
			return new ArrayList<>();
		}

		return AllButtonObjectInGame.getPickUpPlankPoints(clickPoint, nowPoint);
	}

	/**
	 * 移动到指定地点操作
	 * 
	 * @param targetPoint
	 */
	private void move(Point targetPoint) {
		if (canMove(targetPoint)) {
			// 目标地点的图片 ——> 改为有人站在上面，原位置把人去掉
			String targetBeforeImageName = allButtonObjectInGame.getButtonObjectByPoint(targetPoint).imageName;
			String nowBeforeImageName = allButtonObjectInGame.getButtonObjectByPoint(nowPoint).imageName;
			allButtonObjectInGame.changeButtonImage(targetPoint, targetBeforeImageName + "_man");
			allButtonObjectInGame.changeButtonImage(nowPoint,
					nowBeforeImageName.substring(0, nowBeforeImageName.length() - 4));
			nowPoint = targetPoint;
		}
	}

	/**
	 * 判断指定地点是否可达
	 * 
	 * @param targetPoint
	 * @return
	 */
	private boolean canMove(Point targetPoint) {
		// 点击地点是水面，不可达
		if (allButtonObjectInGame.getButtonObjectByPoint(targetPoint).imageName.startsWith("water")) {
			return false;
		}
		// 点击地点是岸边（不是stump）,不可导
		if (allButtonObjectInGame.getButtonObjectByPoint(targetPoint).imageName.startsWith("bank")) {
			return false;
		}
		// 准备斜着走，不可达
		if (targetPoint.x != nowPoint.x && targetPoint.y != targetPoint.y) {
			return false;
		}
		// 往竖直方向走
		if (targetPoint.x != nowPoint.x) {
			for (int i = Integer.min(targetPoint.x, nowPoint.x); i <= Integer.max(targetPoint.x, nowPoint.x); i++) {
				if (!allButtonObjectInGame.getButtonObjectByPoint(new Point(i, nowPoint.y)).isPlayerCanStand()) {
					return false;
				}
			}
		} else {
			// 往水平方向走
			for (int i = Integer.min(targetPoint.y, nowPoint.y); i <= Integer.max(targetPoint.y, nowPoint.y); i++) {
				if (!allButtonObjectInGame.getButtonObjectByPoint(new Point(nowPoint.x, i)).isPlayerCanStand()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 玩家获胜后的操作
	 */
	private void win() {
		// 关闭游戏页面
		gameWindow.dispose();
		// 打开玩家最终耗时界面
		new WinScene((int) ((System.currentTimeMillis() - startTime) / 1000));
	}
}
