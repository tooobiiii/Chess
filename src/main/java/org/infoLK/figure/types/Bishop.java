package org.infoLK.figure.types;

import org.infoLK.constants.FigureType;
import org.infoLK.figure.AChessFigure;
import org.infoLK.game.PlayerTeam;
import org.infoLK.gui.board.ChessBoardTile;

import java.util.Set;

public final class Bishop extends AChessFigure {

	public Bishop(ChessBoardTile tile, PlayerTeam team) {
		super(tile, team);
		this.type = FigureType.BISHOP;
	}

	@Override
	public Set<ChessBoardTile> suggestMoves() {
		calculateMoves(suggestedMoves, 1, 1);  // Top-right diagonal
		calculateMoves(suggestedMoves, 1, -1); // Top-left diagonal
		calculateMoves(suggestedMoves, -1, 1); // Bottom-right diagonal
		calculateMoves(suggestedMoves, -1, -1); // Bottom-left diagonal
		return suggestedMoves;
	}
}
