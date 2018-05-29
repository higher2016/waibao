package images;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * 本类管理所有游戏要用到的图片（加载目录为./images/.）
 */
public class AllImages {
	// key表示图片的名字（不带后缀——.jpg）
	private static Map<String, ImageIcon> allImageIcons = new HashMap<>();
	// 图片所在目录
	private static final String pictureURL = System.getProperty("user.dir") + File.separator + "images";

	/**
	 * 从指定目录加载游戏所有要用到的图片进内存中
	 */
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

	/**
	 * 根据图片名字，获取图片对象
	 */
	public static ImageIcon getImageIconBuyName(String name) {
		return allImageIcons.get(name);
	}
}
