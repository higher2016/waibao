package scene;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.StartGame;
import scene.position.AllSceneInitPosition;
import scene.position.AllSceneSize;

public class CongratulationScene {
	private JPanel bottom = new JPanel();
	private JPanel bottom2 = new JPanel();
	private JButton nextGameButton = new JButton();
	private JButton backToHomePage = new JButton();

	private JFrame jframe = new JFrame();

	public CongratulationScene(String passLevelName, boolean isHigherLevel, double gameUseTime) {
		jframe.setTitle("Congratulation " + StartGame.player.getPlayerName() + " pass level —— " + passLevelName);
		if (!isHigherLevel) {
			initNextGameButton();
		}
		initBackToHomePage();

		JLabel congratulation = new JLabel();
		congratulation.setText("Congratulation " + StartGame.player.getPlayerName() + " pass level —— " + passLevelName);
		congratulation.setFont(new Font("Arial", Font.ITALIC, 18));
		bottom.add(congratulation);

		bottom2.add(nextGameButton);
		bottom2.add(backToHomePage);

		jframe.add(bottom, BorderLayout.NORTH);
		jframe.add(bottom2, BorderLayout.SOUTH);
		jframe.setSize(AllSceneSize.congratulationSize.width, AllSceneSize.congratulationSize.height);
		jframe.setLocation(AllSceneInitPosition.Congratulation.x, AllSceneInitPosition.Congratulation.y);
		jframe.setVisible(true);
	}

	private void initBackToHomePage() {
		backToHomePage.setText("Back to home page");
		backToHomePage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jframe.dispose();
				StartGame.mainScene.setVisible(true);
			}
		});
		jframe.add(backToHomePage);
	}

	private void initNextGameButton() {
		nextGameButton.setText("Continue to challenge");
		nextGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartGame.player.startGame();
				jframe.dispose();
			}
		});
		jframe.add(nextGameButton);
	}
}
