package main;

import java.awt.Point;

import javax.swing.JButton;

/**
 * 游戏中单独的像素单元，一个单元包含一个按钮，像素所在的位置和按钮的图片
 */
public class ButtonObject {
	// 像素点位置
	public final Point point;
	// 像素点的按钮
	public JButton button;
	// 像素点的图片
	public String imageName;

	public ButtonObject(Point point, JButton button, String imageName) {
		this.point = point;
		this.button = button;
		this.imageName = imageName;
	}

	public boolean isPlayerCanStand() {
		return imageName.startsWith("plank") || imageName.startsWith("stump");
	}
}
