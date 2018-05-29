package player;

import scene.Game;

public class Player {
	private final String playerName;
	private boolean isStartGame;
	private String thisGameLevel;
	private String nextGameLevel;

	public void startGame() {
		if (nextGameLevel == null) {
			new Game("Level3");
			isStartGame = true;
		} else {
			new Game(nextGameLevel);
			isStartGame = true;
		}
	}

	public void endGame(String nextGame) {
		isStartGame = false;
		this.nextGameLevel = nextGame;
	}

	public Player(String playerName) {
		this.playerName = playerName;
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
}
