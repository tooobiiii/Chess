package me.tooobiiii.figure.types;

import me.tooobiiii.constants.FigureType;
import me.tooobiiii.figure.AChessFigure;
import me.tooobiiii.game.PlayerTeam;
import me.tooobiiii.gui.board.ChessBoardTile;

import java.util.Set;

public final class Queen extends AChessFigure {

	public Queen(ChessBoardTile tile, PlayerTeam team) {
		super(tile, team);
		this.type = FigureType.QUEEN;
	}

	@Override
	public Set<ChessBoardTile> suggestMoves() {
		suggestedMoves.clear();
		calculateMoves(suggestedMoves, 1, 1);  // Top-right
		calculateMoves(suggestedMoves, 1, -1); // Top-left
		calculateMoves(suggestedMoves, -1, 1); // Bottom-right
		calculateMoves(suggestedMoves, -1, -1); // Bottom-left

		calculateMoves(suggestedMoves,1, 0);  // Down
		calculateMoves(suggestedMoves, -1, 0); // Up
		calculateMoves(suggestedMoves, 0, 1);  // Right
		calculateMoves(suggestedMoves, 0, -1); // Left
		return suggestedMoves;
	}
}
