package images;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class AllImages {
	private static Map<String, ImageIcon> allImageIcons = new HashMap<>();
	private static final String pictureURL = System.getProperty("user.dir") + File.separator + "images";

	static {
		File file = new File(pictureURL);
		for (File file2 : file.listFiles()) {
			if (!file2.isDirectory()) {
				// 获得image名称（去掉后缀）
				String name = file2.getName().substring(0, file2.getName().length() - 4);

				ImageIcon icon = new ImageIcon(file2.getAbsolutePath());
				Image img = icon.getImage();
				Image newimg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);

				allImageIcons.put(name, new ImageIcon(newimg));
			}
		}
	}

	public static ImageIcon getImageIconBuyName(String name) {
		return allImageIcons.get(name);
	}

	public static void main(String[] args) {
		System.out.println(allImageIcons);
	}
}
