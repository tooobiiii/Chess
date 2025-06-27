package org.infoLK.figure.types;

import org.infoLK.constants.FigureType;
import org.infoLK.figure.AChessFigure;
import org.infoLK.game.PlayerTeam;
import org.infoLK.gui.board.ChessBoard;
import org.infoLK.gui.board.ChessBoardTile;

import java.util.Set;

public class Pawn extends AChessFigure {

	private boolean hasMoved = false;

	public Pawn(ChessBoardTile tile, PlayerTeam team) {
		super(tile, team);
		this.type = FigureType.PAWN;
	}

	@Override
	public Set<ChessBoardTile> suggestMoves() {
		int direction = team == PlayerTeam.WHITE ? -1 : 1; // White moves up (-1), Black moves down (+1)
		int currentRow = tile.getRow();
		int currentCol = tile.getColumn();

		// Forward move
		ChessBoardTile forwardTile = ChessBoard.getTileAt(currentRow + direction, currentCol);
		if (forwardTile != null && !forwardTile.isOccupied()) {
			suggestedMoves.add(forwardTile);

			// Double move on first move
			if (!hasMoved) {
				ChessBoardTile doubleForwardTile = ChessBoard.getTileAt(currentRow + 2 * direction, currentCol);
				if (doubleForwardTile != null && !doubleForwardTile.isOccupied()) {
					suggestedMoves.add(doubleForwardTile);
				}
			}
		}

		// Diagonal captures
		ChessBoardTile leftDiagonal = ChessBoard.getTileAt(currentRow + direction, currentCol - 1);
		ChessBoardTile rightDiagonal = ChessBoard.getTileAt(currentRow + direction, currentCol + 1);

		if (leftDiagonal != null && leftDiagonal.isOccupiedByOpponent(this)) {
			suggestedMoves.add(leftDiagonal);
		}
		if (rightDiagonal != null && rightDiagonal.isOccupiedByOpponent(this)) {
			suggestedMoves.add(rightDiagonal);
		}
		return suggestedMoves;
	}

	@Override
	public void moveFigure(ChessBoardTile target) {
		super.moveFigure(target);
		hasMoved = true;
	}
}
