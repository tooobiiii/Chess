package org.infoLK.figure.types;

import org.infoLK.constants.FigureType;
import org.infoLK.figure.AChessFigure;
import org.infoLK.game.PlayerTeam;
import org.infoLK.gui.board.ChessBoard;
import org.infoLK.gui.board.ChessBoardTile;

import java.util.HashSet;
import java.util.Set;

public class Rook extends AChessFigure {

	public Rook(ChessBoardTile tile, PlayerTeam team) {
		super(tile, team);
		this.type = FigureType.ROOK;
	}

	@Override
	public Set<ChessBoardTile> suggestMoves() {
		Set<ChessBoardTile> suggestedMoves = new HashSet<>();
		addDiagonalMoves(suggestedMoves, 1, 0); // Down
		addDiagonalMoves(suggestedMoves, -1, 0); // Up
		addDiagonalMoves(suggestedMoves, 0, 1); // Right
		addDiagonalMoves(suggestedMoves, 0, -1); // Left
		return suggestedMoves;
	}

	private void addDiagonalMoves(Set<ChessBoardTile> suggestedMoves, int rowIncrement, int colIncrement) {
		for (int i = 1; i < 8; i++) {
			ChessBoardTile newTile = ChessBoard.getTileAt(tile.getRow() + i * rowIncrement, tile.getColumn() + i * colIncrement);
			if (moveCheck(newTile)) {
				suggestedMoves.add(newTile);
			} else {
				break;
			}
		}
	}
}
