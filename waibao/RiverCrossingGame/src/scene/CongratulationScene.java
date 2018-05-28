package scene;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import main.StartGame;

public class CongratulationScene {
	private JTextArea textArea = new JTextArea();
	private JButton nextGameButton = new JButton();
	private JButton backToHomePage = new JButton();

	private JFrame jframe = new JFrame();

	public CongratulationScene(String passLevelName, boolean isHigherLevel, double gameUseTime) {
		jframe.setTitle("Congratulation " + StartGame.player.getPlayerName() + " pass level —— " + passLevelName);
		if (!isHigherLevel) {
			initNextGameButton();
		}
		initBackToHomePage();
		textArea.setText("Congratulation " + StartGame.player.getPlayerName() + " pass level —— " + passLevelName);
		jframe.add(textArea);
	}

	private void initBackToHomePage() {
		backToHomePage.setText("Back to home page");
		backToHomePage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jframe.dispose();
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
