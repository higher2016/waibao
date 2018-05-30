package main;

import java.awt.Point;

import javax.swing.JButton;

public class ButtonObject {
	public final Point point;
	public JButton button;
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
