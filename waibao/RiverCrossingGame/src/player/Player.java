package player;

import scene.Game;

public class Player {
	private final String playerName;
	private Long startTime;
	private Long endTime;
	private boolean isStartGame;
	private Game game;
	private String thisGameLevel;
	private String nextGameLevel;

	public void startGame() {
		if (nextGameLevel == null) {
			game = new Game("Level1");
			isStartGame = true;
			startTime = System.currentTimeMillis();
		} else {
			game = new Game(nextGameLevel);
			isStartGame = true;
			startTime = System.currentTimeMillis();
		}
	}

	public void endGame() {
		game = null;
		endTime = System.currentTimeMillis();
	}

	public Player(String playerName) {
		this.playerName = playerName;
	}

	public long getUserTime() {
		if (startTime == null)
			return 0;
		else
			return System.currentTimeMillis() - startTime / 1000;
	}

	public Long getStartTime() {
		return startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public boolean isStartGame() {
		return isStartGame;
	}

	public void setStartGame(boolean isStartGame) {
		this.isStartGame = isStartGame;
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getThisGameLevel() {
		return thisGameLevel;
	}

	public void setThisGameLevel(String thisGameLevel) {
		this.thisGameLevel = thisGameLevel;
	}

	public String getNextGameLevel() {
		return nextGameLevel;
	}

	public void setNextGameLevel(String nextGameLevel) {
		this.nextGameLevel = nextGameLevel;
	}

}
