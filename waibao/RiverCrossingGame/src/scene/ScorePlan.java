package scene;

import java.awt.HeadlessException;
import java.util.List;

import javax.swing.JFrame;

import scene.position.AllSceneInitPosition;
import scene.position.AllSceneSize;

/**
 * 排行榜面板
 */
public class ScorePlan extends JFrame {

	private static final long serialVersionUID = 1L;

	private List<PlayScore> level1ScoreList;
	private List<PlayScore> level2ScoreList;
	private List<PlayScore> level3ScoreList;

	public ScorePlan() throws HeadlessException {
		setSize(AllSceneSize.scorePlanSize.width, AllSceneSize.scorePlanSize.height);
		setLocation(AllSceneInitPosition.ScorePlanPosition.x, AllSceneInitPosition.ScorePlanPosition.y);
		this.setTitle("Score list");
		this.setVisible(true);
	}

	public void addPlayScore(PlayScore playScore) {
		List<PlayScore> targetScoreList = null;
		if (playScore.levelName == "Level1") {
			targetScoreList = level1ScoreList;
		} else if (playScore.levelName == "Level2") {
			targetScoreList = level2ScoreList;
		} else if (playScore.levelName == "Level3") {
			targetScoreList = level3ScoreList;
		}
		boolean isAdd = false;
		for (int i = 0; i < targetScoreList.size(); i++) {
			if (targetScoreList.get(i).useSecond > playScore.useSecond) {
				targetScoreList.add(i, playScore);
				isAdd = true;
				break;
			}
		}

		if (!isAdd && targetScoreList.size() < 10) {
			targetScoreList.add(playScore);
		}
		if (targetScoreList.size() > 10) {
			targetScoreList.remove(targetScoreList.size() - 1);
		}
	}

	public void writeBackToText() {

	}

	public static class PlayScore {
		private String playName;
		private String levelName;
		private double useSecond;

		public PlayScore(String playName, String levelName, double useSecond) {
			this.playName = playName;
			this.levelName = levelName;
			this.useSecond = useSecond;
		}

		@Override
		public String toString() {
			return "playName=" + playName + ", levelName=" + levelName + ", useSecond=" + useSecond;
		}
	}

}
