package me.tooobiiii.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.tooobiiii.gui.board.ChessBoard;

import java.awt.*;

@NoArgsConstructor
@AllArgsConstructor
public enum PlayerTeam {

	WHITE(Color.WHITE), BLACK(Color.BLACK), NONE;

	@Getter
	private static PlayerTeam currentTurn = WHITE;
	@Getter
	private Color color;

	public static void switchTurn() {
		PlayerTeam previousTurn = currentTurn;
		currentTurn = (currentTurn == WHITE) ? BLACK : WHITE;
		if (ChessBoard.isCheckMate(currentTurn)) {
			ChessBoard.getGraphic().getTurnLabel().setText(previousTurn + " wins!");
			currentTurn = NONE;
		} else
			ChessBoard.getGraphic().updateTurnLabel();
	}

	public static PlayerTeam getOpponent() {
		return (currentTurn == WHITE) ? BLACK : WHITE;
	}
}
