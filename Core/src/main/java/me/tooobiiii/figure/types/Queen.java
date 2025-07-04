package me.tooobiiii.figure.types;

import me.tooobiiii.constants.FigureType;
import me.tooobiiii.figure.AChessFigure;
import me.tooobiiii.game.PlayerTeam;
import me.tooobiiii.gui.board.ChessBoard;
import me.tooobiiii.gui.board.ChessBoardTile;

import java.util.HashSet;
import java.util.Set;

public final class Queen extends AChessFigure {

	public Queen(ChessBoard board, ChessBoardTile tile, PlayerTeam team) {
		super(board, tile, team);
		this.type = FigureType.QUEEN;
	}

	@Override
	public Set<ChessBoardTile> suggestMoves() {
		Set<ChessBoardTile> moves = new HashSet<>();
		calculateMoves(moves, 1, 1);  // Top-right diagonal
		calculateMoves(moves, 1, -1); // Top-left diagonal
		calculateMoves(moves, -1, 1); // Bottom-right diagonal
		calculateMoves(moves, -1, -1); // Bottom-left diagonal

		calculateMoves(moves, 1, 0);  // Down
		calculateMoves(moves, -1, 0); // Up
		calculateMoves(moves, 0, 1);  // Right
		calculateMoves(moves, 0, -1); // Left

		return moves;
	}
}
