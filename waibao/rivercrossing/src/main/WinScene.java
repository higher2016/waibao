package main;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * 获胜页面
 */
public class WinScene extends JFrame {
	private JPanel plane = new JPanel();
	private JTextArea word = new JTextArea();

	public WinScene(int spentTime) {
		setTitle("WIN PAGE");
		word.setText("The total time you have spent: " + spentTime + "s");
		word.setFont(new Font("Arial", Font.TYPE1_FONT, 22));

		plane.add(word);
		add(plane);
		setSize(400, 100);
		setLocation(500, 50);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
