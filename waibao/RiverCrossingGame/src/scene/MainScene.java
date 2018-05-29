package scene;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.StartGame;
import scene.position.AllSceneInitPosition;
import scene.position.AllSceneSize;

/**
 * 主界面，显示选项 —— 1、分数排行榜；2、游戏关卡选择；3、游戏玩法介绍
 */
public class MainScene extends JFrame {
	private static final long serialVersionUID = 1L;

	private static final String BUTTONS_NAME_SCORE = "Score list";
	private static final String BUTTONS_START_GAME = "Start Game";
	private static final String BUTTONS_NAME_INTRODUCTION = "How to play";

	private JPanel bottom = new JPanel();
	private ScorePlan scorePlan;// 分数面板 
	private Introduce introduce;// 游戏介绍面板

	public MainScene() {
		addIntroductionButton();
		addStarGame();
		addScoreButton();
		addExitButton();
		setMainSceneConfig();
	}

	/**
	 * 调整主面板的基本设置（窗口大小、显示位置、标题）
	 */
	private void setMainSceneConfig() {
		// 设置大小，按像素计算
		setSize(AllSceneSize.mainSize.width, AllSceneSize.mainSize.height);
		// 设置面板初始位置
		setLocation(AllSceneInitPosition.MainPosition.x, AllSceneInitPosition.MainPosition.y);
		this.setTitle("River Crossing");
		this.setVisible(true);
		this.add(bottom, BorderLayout.EAST);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * 添加“游戏介绍”按钮，点击弹出游戏介绍页面
	 */
	private void addIntroductionButton() {
		JButton introduction = new JButton(BUTTONS_NAME_INTRODUCTION);
		bottom.add(introduction);
		introduction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (introduce == null) {
					introduce = new Introduce();
				} else {
					introduce.setVisible(true);
				}
			}
		});
	}

	/**
	 * 添加“分数排行榜”按钮，点击弹出玩家分数排行榜
	 */
	private void addScoreButton() {
		JButton score = new JButton(BUTTONS_NAME_SCORE);
		bottom.add(score);
		scorePlan = new ScorePlan();
		score.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scorePlan = new ScorePlan();
				scorePlan.setVisible(true);
			}
		});
	}

	/**
	 * 添加退出游戏按钮
	 */
	private void addExitButton() {
		JButton exit = new JButton("Exit");
		bottom.add(exit);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
	}

	/**
	 * 开始游戏按钮
	 */
	private void addStarGame() {
		JButton startGame = new JButton(BUTTONS_START_GAME);
		bottom.add(startGame);
		startGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartGame.player.startGame();
			}
		});
	}

	public ScorePlan getScorePlan() {
		return scorePlan;
	}

	public void setScorePlan(ScorePlan scorePlan) {
		this.scorePlan = scorePlan;
	}
}
