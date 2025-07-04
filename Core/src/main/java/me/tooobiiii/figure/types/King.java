package me.tooobiiii.figure.types;

import me.tooobiiii.constants.FigureType;
import me.tooobiiii.figure.AChessFigure;
import me.tooobiiii.game.PlayerTeam;
import me.tooobiiii.gui.board.ChessBoard;
import me.tooobiiii.gui.board.ChessBoardTile;

import java.util.HashSet;
import java.util.Set;

public final class King extends AChessFigure {

	private static final int[][] KING_MOVE_OFFSETS = {
			{1, 0}, {-1, 0}, {0, 1}, {0, -1},
			{1, 1}, {1, -1}, {-1, 1}, {-1, -1}
	};

	public King(ChessBoard board, ChessBoardTile tile, PlayerTeam team) {
		super(board, tile, team);
		this.type = FigureType.KING;
	}

	@Override
	public Set<ChessBoardTile> suggestMoves() {
		Set<ChessBoardTile> moves = new HashSet<>();
		addStaticMoves(moves, KING_MOVE_OFFSETS, tile.getRow(), tile.getColumn());
		return moves;
	}

}