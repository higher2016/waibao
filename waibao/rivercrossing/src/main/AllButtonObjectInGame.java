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

public class AllButtonObjectInGame extends JFXPanel {
	public List<List<ButtonObject>> buttonObjects = new ArrayList<>();
	private Map<String, ImageIcon> allImages = new HashMap<>();

	public AllButtonObjectInGame() {
		loadAllImages();
		setPreferredSize(new Dimension(9 * 50, 13 * 50));
		initAllButtonObject();
	}

	public void changeButtonImage(Point p, String newImageName) {
		ButtonObject bo = getButtonObjectByPoint(p);
		bo.button.setIcon(allImages.get(newImageName));
		bo.imageName = newImageName;
	}

	public void setButtonObjects(List<List<ButtonObject>> buttonObjects) {
		this.buttonObjects = buttonObjects;
	}

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

	public ButtonObject getButtonObjectByPoint(Point point) {
		return buttonObjects.get(point.y).get(point.x);
	}

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
