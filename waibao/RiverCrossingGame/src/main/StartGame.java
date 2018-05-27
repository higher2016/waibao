package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import player.Player;
import scene.MainScene;

public class StartGame {
	private JFrame startPage = new JFrame();

	public static Player player;

	private void initStartPage() {
		startPage.setSize(600, 300);
		startPage.setLocation(100, 100);

		JPanel jp = new JPanel();
		JLabel emptyLable = new JLabel("Please entry your name and start the game.");
		JTextField playerName = new JTextField(30);
		JButton ok = new JButton("start travel");
		jp.add(emptyLable);
		jp.add(playerName);
		jp.add(ok);
		startPage.add(jp, BorderLayout.CENTER);

		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player = new Player(playerName.getText());
				new MainScene();
				startPage.dispose();
			}
		});
		startPage.setVisible(true);
	}

	public static void main(String[] args) {
		new StartGame().initStartPage();
	}
}
