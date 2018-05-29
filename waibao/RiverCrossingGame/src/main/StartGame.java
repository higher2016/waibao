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

/**
 * 游戏主界面，该类为游戏启动类
 */
public class StartGame {
	private JFrame startPage = new JFrame();

	public static Player player; // 玩家对象，用于保存登录玩家的玩家名等信息
	public static MainScene mainScene; // 游戏界面主页

	private void initStartPage() {
		// 设定游戏界面大小和屏幕初始位置
		startPage.setSize(600, 300);
		startPage.setLocation(100, 100);

		// 加载主界面各类按钮
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
				if (playerName.getText() != null && !playerName.getText().equals("")) {
					player = new Player(playerName.getText());
					mainScene = new MainScene();
					startPage.dispose();
				}
			}
		});
		startPage.setVisible(true);
	}

	public static void main(String[] args) {
		new StartGame().initStartPage();
	}
}
