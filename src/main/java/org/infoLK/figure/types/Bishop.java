package org.infoLK.figure.types;

import org.infoLK.constants.FigureType;
import org.infoLK.figure.AChessFigure;
import org.infoLK.game.PlayerTeam;
import org.infoLK.gui.board.ChessBoard;
import org.infoLK.gui.board.ChessBoardTile;

import java.util.HashSet;
import java.util.Set;

public final class Bishop extends AChessFigure {

	public Bishop(ChessBoardTile tile, PlayerTeam team) {
		super(tile, team);
		this.type = FigureType.BISHOP;
	}

	@Override
	public Set<ChessBoardTile> suggestMoves() {
		Set<ChessBoardTile> suggestedMoves = new HashSet<>();
		addDiagonalMoves(suggestedMoves, 1, 1);  // Top-right diagonal
		addDiagonalMoves(suggestedMoves, 1, -1); // Top-left diagonal
		addDiagonalMoves(suggestedMoves, -1, 1); // Bottom-right diagonal
		addDiagonalMoves(suggestedMoves, -1, -1); // Bottom-left diagonal
		return suggestedMoves;
	}

	private void addDiagonalMoves(Set<ChessBoardTile> suggestedMoves, int rowIncrement, int colIncrement) {
		for (int i = 1; i < 8; i++) {
			ChessBoardTile newTile = ChessBoard.getTileAt(tile.getRow() + i * rowIncrement, tile.getColumn() + i * colIncrement);
			if (moveCheck(newTile)) {
				suggestedMoves.add(newTile);
			} else
				break;
		}
	}
}
