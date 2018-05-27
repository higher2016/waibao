package scene;

import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JLabel;

import scene.position.AllSceneInitPosition;
import scene.position.AllSceneSize;
import unit.ReadGameModelUnit;

public class Introduce extends JFrame {

	private static final long serialVersionUID = 1L;

	public Introduce() throws HeadlessException {
		setSize(AllSceneSize.introducePlanSize.width, AllSceneSize.introducePlanSize.height);
		setLocation(AllSceneInitPosition.IntroducePosition.x, AllSceneInitPosition.IntroducePosition.y);
		JLabel introduceStr = new JLabel();
		introduceStr.setText(ReadGameModelUnit.readIntroduceStr());
		introduceStr.setFont(new Font("Arial", Font.ITALIC, 14));
		this.setTitle("The rule of \"River Crossing\"");
		this.add(introduceStr);
		this.setVisible(true);
	}
}
