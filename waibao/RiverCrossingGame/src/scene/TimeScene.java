package scene;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import scene.position.AllSceneInitPosition;

@SuppressWarnings("serial")
public class TimeScene extends JFrame {
	private JPanel plane = new JPanel();
	private JTextArea word = new JTextArea();
	private JLabel useTime = new JLabel();

	public TimeScene() {
		setTitle("Use Time");
		word.setText("The time you have spent: ");
		word.setFont(new Font("Arial", Font.ITALIC, 20));

		useTime.setText("0.00");
		useTime.setFont(new Font("Arial", Font.BOLD, 28));

		plane.add(word);
		plane.add(useTime);
		add(plane);
		setSize(400, 100);
		setLocation(AllSceneInitPosition.Time.x, AllSceneInitPosition.Time.y);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void changeUseTime(int useTime) {
		this.useTime.setText(useTime + "s");
		this.useTime.setFont(new Font("Arial", Font.BOLD, 28));
	}
}
