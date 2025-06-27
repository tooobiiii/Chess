package org.infoLK.figure.types;

import org.infoLK.constants.FigureType;
import org.infoLK.figure.AChessFigure;
import org.infoLK.game.PlayerTeam;
import org.infoLK.gui.board.ChessBoardTile;

import java.util.Set;

public final class King extends AChessFigure {

	public King(ChessBoardTile tile, PlayerTeam team) {
		super(tile, team);
		this.type = FigureType.KING;
	}

	@Override
	public Set<ChessBoardTile> suggestMoves() {
		// Define all possible king moves as row and column offsets
		int[][] moveOffsets = {
				{1, 0}, {-1, 0}, {0, 1}, {0, -1}, // Vertical and horizontal moves
				{1, 1}, {1, -1}, {-1, 1}, {-1, -1} // Diagonal moves
		};
		addStaticMoves(suggestedMoves, moveOffsets, tile.getRow(), tile.getColumn());
		return suggestedMoves;
	}
}