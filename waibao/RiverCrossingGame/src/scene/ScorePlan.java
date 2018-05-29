package scene;

import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTable;

import scene.position.AllSceneInitPosition;
import scene.position.AllSceneSize;
import unit.ReadGameModelUnit;

/**
 * 排行榜面板
 */
public class ScorePlan extends JFrame {

	private static final long serialVersionUID = 1L;

	private Map<String, List<PlayScore>> scoreMap = new HashMap<String, List<PlayScore>>();

	public ScorePlan() throws HeadlessException {
		setSize(AllSceneSize.scorePlanSize.width, AllSceneSize.scorePlanSize.height);
		setLocation(AllSceneInitPosition.ScorePlanPosition.x, AllSceneInitPosition.ScorePlanPosition.y);
		parseScoreStr();
		this.setTitle("Score list");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		List<PlayScore> s = new ArrayList<PlayScore>();
		int lineNum = 0;
		for (Map.Entry<String, List<PlayScore>> entry : scoreMap.entrySet()) {
			s.addAll(entry.getValue());
			lineNum += entry.getValue().size();
		}
		lineNum += scoreMap.size() * 3;
		JTable jTable = new JTable(lineNum, 1);
		jTable.setRowHeight(14);
		jTable.setPreferredSize(new java.awt.Dimension(500, 400));
		jTable.setValueAt("               SCORE BOARD", 0, 0);
		int i = 0;
		for (Map.Entry<String, List<PlayScore>> entry : scoreMap.entrySet()) {
			int x = 1;
			for (PlayScore playScore : entry.getValue()) {
				jTable.setValueAt("The Rank: " + x + "th : " + playScore.string(), i, 0);
				x++;
				i++;
			}
			jTable.setValueAt("", i, 0);
			jTable.setValueAt("", i, 0);
			jTable.setValueAt("", i, 0);
		}
		add(jTable);
	}

	private void parseScoreStr() {
		String scoreStr = ReadGameModelUnit.readScoreStr();
		String scoreStrs[] = scoreStr.split(System.getProperty("line.separator"));
		for (String s : scoreStrs) {
			PlayScore playScore = PlayScore.parse(s);
			List<PlayScore> playScores = scoreMap.get(playScore.levelName);
			if (playScores == null) {
				playScores = new ArrayList<PlayScore>();
				scoreMap.put(playScore.levelName, playScores);
			}
			playScores.add(playScore);
		}
	}

	public void addPlayScore(PlayScore playScore) {
		List<PlayScore> targetScoreList = scoreMap.get(playScore.levelName);
		if (targetScoreList == null) {
			targetScoreList = new ArrayList<PlayScore>();
			scoreMap.put(playScore.levelName, targetScoreList);
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
		writeBackToText();
	}

	public void writeBackToText() {
		String allSocre = "";
		for (Map.Entry<String, List<PlayScore>> entry : scoreMap.entrySet()) {
			for (PlayScore playScore : entry.getValue()) {
				allSocre += playScore.toString() + System.getProperty("line.separator");
			}
		}
		ReadGameModelUnit.appendInfoToFile(allSocre);
	}

	public static class PlayScore {
		private String playName;
		private String levelName;
		private int useSecond;

		public static PlayScore parse(String str) {
			PlayScore playScore = new PlayScore();
			String[] strs = str.split(",");
			playScore.playName = strs[0].trim();
			playScore.levelName = strs[1].trim();
			playScore.useSecond = Integer.valueOf(strs[2].trim());
			return playScore;
		}

		public PlayScore(String playName, String levelName, int useSecond) {
			this.playName = playName;
			this.levelName = levelName;
			this.useSecond = useSecond;
		}

		public PlayScore() {
		}

		public String string() {
			return "player name=" + playName + ", level=" + levelName + ", use time=" + useSecond + "s";
		}

		@Override
		public String toString() {
			return playName + ", " + levelName + ", " + useSecond;
		}
	}

	public static void main(String[] args) {
		ScorePlan plan = new ScorePlan();
		plan.addPlayScore(new PlayScore("as", "Level1", 12));
	}
}
