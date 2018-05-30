package main;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javafx.embed.swing.JFXPanel;

/**
 * 游戏所有像素点的管理类
 */
public class AllButtonObjectInGame extends JFXPanel {
	// 二维像素点列表
	public List<List<ButtonObject>> buttonObjects = new ArrayList<>();
	// 所有要用到的图片
	private Map<String, ImageIcon> allImages = new HashMap<>();

	// 所有合法点击路径
	public static final List<Point> legal1 = new ArrayList<>();
	public static final List<Point> legal2 = new ArrayList<>();
	public static final List<Point> legal3 = new ArrayList<>();
	public static final List<Point> legal4 = new ArrayList<>();
	public static final List<Point> legal5 = new ArrayList<>();

	static {
		legal1.add(new Point(2, 12));
		legal1.add(new Point(2, 11));
		legal1.add(new Point(2, 10));
		legal1.add(new Point(2, 9));
		legal1.add(new Point(2, 8));

		legal2.add(new Point(2, 8));
		legal2.add(new Point(2, 7));
		legal2.add(new Point(2, 6));

		legal3.add(new Point(2, 6));
		legal3.add(new Point(3, 6));
		legal3.add(new Point(4, 6));
		legal3.add(new Point(5, 6));
		legal3.add(new Point(6, 6));

		legal4.add(new Point(6, 6));
		legal4.add(new Point(6, 5));
		legal4.add(new Point(6, 4));
		
		legal5.add(new Point(6, 4));
		legal5.add(new Point(6, 3));
		legal5.add(new Point(6, 2));
		legal5.add(new Point(6, 1));
		legal5.add(new Point(6, 0));
	}

	private static boolean isInStartOrEnd(Point point, List<Point> points) {
		int index = points.indexOf(point);
		return index == 0 || index == points.size() - 1;
	}

	public static List<Point> getPickUpPlankPoints(Point clickPoint, Point nowPoint) {
		if (legal1.indexOf(clickPoint) > 0 && isInStartOrEnd(nowPoint, legal1)) {
			return legal1.subList(1, legal1.size() - 1);
		}
		if (legal2.indexOf(clickPoint) > 0 && isInStartOrEnd(nowPoint, legal2)) {
			return legal2.subList(1, legal2.size() - 1);
		}
		if (legal3.indexOf(clickPoint) > 0 && isInStartOrEnd(nowPoint, legal3)) {
			return legal3.subList(1, legal3.size() - 1);
		}
		if (legal4.indexOf(clickPoint) > 0 && isInStartOrEnd(nowPoint, legal4)) {
			return legal4.subList(1, legal4.size() - 1);
		}
		if (legal5.indexOf(clickPoint) > 0 && isInStartOrEnd(nowPoint, legal5)) {
			return legal5.subList(1, legal5.size() - 1);
		}
		return new ArrayList<>();
	}

	public AllButtonObjectInGame() {
		loadAllImages();
		setPreferredSize(new Dimension(9 * 50, 13 * 50));
		initAllButtonObject();
	}

	/**
	 * 改变像素点的图片
	 * @param p
	 * @param newImageName
	 */
	public void changeButtonImage(Point p, String newImageName) {
		ButtonObject bo = getButtonObjectByPoint(p);
		bo.button.setIcon(allImages.get(newImageName));
		bo.imageName = newImageName;
	}

	public void setButtonObjects(List<List<ButtonObject>> buttonObjects) {
		this.buttonObjects = buttonObjects;
	}

	/**
	 * 根据按钮获取像素点对象
	 * @param jButton
	 * @return
	 */
	public ButtonObject getPointByButton(JButton jButton) {
		for (List<ButtonObject> als : buttonObjects) {
			for (ButtonObject bo : als) {
				if (bo.button == jButton) {
					return bo;
				}
			}
		}
		return null;
	}

	/**
	 * 初始化所有像素点
	 */
	private void initAllButtonObject() {
		String mapStr = readMapString();
		int y = 0;
		for (String mapStr1 : mapStr.split(System.getProperty("line.separator"))) {
			int x = 0;
			List<ButtonObject> inButtonList = new ArrayList<>();
			for (String imageName : mapStr1.split(",")) {
				ImageIcon icon = allImages.get(imageName);
				JButton button = new JButton(icon);
				button.setBorderPainted(false);
				inButtonList.add(new ButtonObject(new Point(x, y), button, imageName));
				this.add(button);
				x++;
			}
			buttonObjects.add(inButtonList);
			y++;
		}
	}

	/**
	 * 根据位置活动像素点对象
	 * @param point
	 * @return
	 */
	public ButtonObject getButtonObjectByPoint(Point point) {
		return buttonObjects.get(point.y).get(point.x);
	}

	/**
	 * 加载地图信息
	 * @return
	 */
	private static String readMapString() {
		File file = new File(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "map");
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(filecontent);
	}

	/**
	 * 加载所有要用的图片
	 */
	private void loadAllImages() {
		File file = new File(System.getProperty("user.dir") + File.separator + "resources");
		for (File file2 : file.listFiles()) {
			if (!file2.isDirectory() && file2.getName().endsWith("jpg")) {
				String name = file2.getName().substring(0, file2.getName().length() - 4);
				ImageIcon icon = new ImageIcon(file2.getAbsolutePath());
				Image img = icon.getImage();
				Image newimg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
				this.allImages.put(name, new ImageIcon(newimg));
			}
		}
	}
}
