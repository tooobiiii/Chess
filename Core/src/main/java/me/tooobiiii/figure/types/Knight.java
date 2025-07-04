package me.tooobiiii.figure.types;

import me.tooobiiii.constants.FigureType;
import me.tooobiiii.figure.AChessFigure;
import me.tooobiiii.game.PlayerTeam;
import me.tooobiiii.gui.board.ChessBoardTile;

import java.util.Set;

public final class Knight extends AChessFigure {

	public Knight(ChessBoardTile tile, PlayerTeam team) {
		super(tile, team);
		this.type = FigureType.KNIGHT;
	}

	@Override
	public Set<ChessBoardTile> suggestMoves() {
		suggestedMoves.clear();
		// Define all possible knight moves as row and column offsets
		int[][] moveOffsets = {
				{2, 1}, {2, -1}, {-2, 1}, {-2, -1},
				{1, 2}, {1, -2}, {-1, 2}, {-1, -2}
		};
		addStaticMoves(suggestedMoves, moveOffsets, tile.getRow(), tile.getColumn());
		return suggestedMoves;
	}
}
