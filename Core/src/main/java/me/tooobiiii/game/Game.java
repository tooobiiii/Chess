package me.tooobiiii.game;

import me.tooobiiii.gui.BootMenu;
import me.tooobiiii.gui.board.ChessBoard;

public final class Game {

	private Game() {
		new BootMenu();
	}

	public void stopGame(PlayerTeam team) {
		ChessBoard.getGraphic().getTurnLabel().setText(team + " wins!");
	}

	public static Game getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private static final class SingletonHolder {
		private static final Game INSTANCE = new Game();
	}
}
