package me.tooobiiii.figure.types;

import me.tooobiiii.constants.FigureType;
import me.tooobiiii.figure.AChessFigure;
import me.tooobiiii.game.PlayerTeam;
import me.tooobiiii.gui.board.ChessBoard;
import me.tooobiiii.gui.board.ChessBoardTile;

import java.util.HashSet;
import java.util.Set;

public final class Knight extends AChessFigure {

	private static final int[][] KNIGHT_MOVE_OFFSETS = {
			{2, 1}, {2, -1}, {-2, 1}, {-2, -1},
			{1, 2}, {1, -2}, {-1, 2}, {-1, -2}
	};

	public Knight(ChessBoard board, ChessBoardTile tile, PlayerTeam team) {
		super(board, tile, team);
		this.type = FigureType.KNIGHT;
	}

	@Override
	public Set<ChessBoardTile> suggestMoves() {
		Set<ChessBoardTile> moves = new HashSet<>();
		addStaticMoves(moves, KNIGHT_MOVE_OFFSETS, tile.getRow(), tile.getColumn());
		return moves;
	}
}
