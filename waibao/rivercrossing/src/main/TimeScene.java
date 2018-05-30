package main;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class TimeScene extends JFrame {
	private JPanel plane = new JPanel();
	private JTextArea word = new JTextArea();
	private JLabel useTime = new JLabel();
	private Font timeFont = new Font("Arial", Font.BOLD, 28);

	public TimeScene() {
		setTitle("Use Time");
		word.setText("The time you have spent: ");
		word.setFont(new Font("Arial", Font.TYPE1_FONT, 22));

		useTime.setText("0.00");
		useTime.setFont(timeFont);

		plane.add(word);
		plane.add(useTime);
		add(plane);
		setSize(400, 100);
		setLocation(500, 50);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void changeUseTime(int useTime) {
		this.useTime.setText(useTime + "s");
		this.useTime.setFont(timeFont);
	}
}
