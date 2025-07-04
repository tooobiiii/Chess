package me.tooobiiii.figure.types;

import me.tooobiiii.constants.FigureType;
import me.tooobiiii.figure.AChessFigure;
import me.tooobiiii.game.PlayerTeam;
import me.tooobiiii.gui.board.ChessBoard;
import me.tooobiiii.gui.board.ChessBoardTile;

import java.util.HashSet;
import java.util.Set;

public final class Bishop extends AChessFigure {

	public Bishop(ChessBoard board, ChessBoardTile tile, PlayerTeam team) {
		super(board, tile, team);
		this.type = FigureType.BISHOP;
	}


	@Override
	public Set<ChessBoardTile> suggestMoves() {
		Set<ChessBoardTile> moves = new HashSet<>();
		calculateMoves(moves, 1, 1);
		calculateMoves(moves, 1, -1);
		calculateMoves(moves, -1, 1);
		calculateMoves(moves, -1, -1);
		return moves;
	}

}