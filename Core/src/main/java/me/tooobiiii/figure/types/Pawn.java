package me.tooobiiii.figure.types;

import me.tooobiiii.constants.FigureType;
import me.tooobiiii.figure.AChessFigure;
import me.tooobiiii.game.PlayerTeam;
import me.tooobiiii.gui.board.ChessBoard;
import me.tooobiiii.gui.board.ChessBoardTile;

import java.util.HashSet;
import java.util.Set;

public final class Pawn extends AChessFigure {

	private boolean hasMoved = false;

	public Pawn(ChessBoard board, ChessBoardTile tile, PlayerTeam team) {
		super(board, tile, team);
		this.type = FigureType.PAWN;
	}

	@Override
	public Set<ChessBoardTile> suggestMoves() {
		Set<ChessBoardTile> moves = new HashSet<>();
		int direction = team == PlayerTeam.WHITE ? -1 : 1; // White moves up, Black down
		int currentRow = tile.getRow();
		int currentCol = tile.getColumn();

		// Forward move
		ChessBoardTile forwardTile = board.getTileAt(currentRow + direction, currentCol);
		if (forwardTile != null && !forwardTile.isOccupied()) {
			moves.add(forwardTile);

			// Double step if not moved
			if (!hasMoved) {
				ChessBoardTile doubleForwardTile = board.getTileAt(currentRow + 2 * direction, currentCol);
				if (doubleForwardTile != null && !doubleForwardTile.isOccupied()) {
					moves.add(doubleForwardTile);
				}
			}
		}

		// Diagonal captures
		ChessBoardTile leftDiagonal = board.getTileAt(currentRow + direction, currentCol - 1);
		ChessBoardTile rightDiagonal = board.getTileAt(currentRow + direction, currentCol + 1);

		if (leftDiagonal != null && leftDiagonal.isOccupiedByOpponent(this)) {
			moves.add(leftDiagonal);
		}
		if (rightDiagonal != null && rightDiagonal.isOccupiedByOpponent(this)) {
			moves.add(rightDiagonal);
		}
		return moves;
	}

	@Override
	public void moveFigure(ChessBoardTile target) {
		super.moveFigure(target);
		hasMoved = true;
	}
}
