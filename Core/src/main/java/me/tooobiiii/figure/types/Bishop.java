package me.tooobiiii.figure.types;

import me.tooobiiii.constants.FigureType;
import me.tooobiiii.figure.AChessFigure;
import me.tooobiiii.game.PlayerTeam;
import me.tooobiiii.gui.board.ChessBoardTile;

import java.util.Set;

public final class Bishop extends AChessFigure {

	public Bishop(ChessBoardTile tile, PlayerTeam team) {
		super(tile, team);
		this.type = FigureType.BISHOP;
	}

	@Override
	public Set<ChessBoardTile> suggestMoves() {
		suggestedMoves.clear();
		calculateMoves(suggestedMoves, 1, 1);  // Top-right diagonal
		calculateMoves(suggestedMoves, 1, -1); // Top-left diagonal
		calculateMoves(suggestedMoves, -1, 1); // Bottom-right diagonal
		calculateMoves(suggestedMoves, -1, -1); // Bottom-left diagonal
		return suggestedMoves;
	}
}