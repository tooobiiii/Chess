package org.infoLK.figure.types;

import org.infoLK.constants.FigureType;
import org.infoLK.figure.AChessFigure;
import org.infoLK.game.PlayerTeam;
import org.infoLK.gui.board.ChessBoardTile;

import java.util.Set;

public class Rook extends AChessFigure {

	public Rook(ChessBoardTile tile, PlayerTeam team) {
		super(tile, team);
		this.type = FigureType.ROOK;
	}

	@Override
	public Set<ChessBoardTile> suggestMoves() {
		calculateMoves(suggestedMoves, 1, 0); // Down
		calculateMoves(suggestedMoves, -1, 0); // Up
		calculateMoves(suggestedMoves, 0, 1); // Right
		calculateMoves(suggestedMoves, 0, -1); // Left
		return suggestedMoves;
	}
}
