package me.tooobiiii.figure;

import lombok.Getter;
import lombok.Setter;
import me.tooobiiii.constants.FigureType;
import me.tooobiiii.game.PlayerTeam;
import me.tooobiiii.gui.board.ChessBoard;
import me.tooobiiii.gui.board.ChessBoardTile;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public abstract class AChessFigure {

	protected ChessBoard board;
	protected ChessBoardTile tile;
	protected FigureType type;
	protected PlayerTeam team;

	protected AChessFigure(ChessBoard board, ChessBoardTile tile, PlayerTeam team) {
		this.board = board;
		this.tile = tile;
		this.team = team;
	}

	/**
	 * Calculate and return all suggested moves for this figure.
	 * Each call should return a fresh Set.
	 */
	public abstract Set<ChessBoardTile> suggestMoves();

	/**
	 * Moves the figure to the target tile if valid.
	 */
	public void moveFigure(ChessBoardTile target) {
		board.getController().attemptMove(this, target);
	}

	/**
	 * Checks if the move to target tile is valid.
	 * Disallows moving onto a tile occupied by the same team.
	 */
	protected boolean moveCheck(ChessBoardTile target) {
		if (target == null) return false;
		PlayerTeam currentTurn = board.getController().getCurrentTurn();
		// Only allow moves if it's the figure's turn
		if (team != currentTurn) return false;

		if (target.isOccupied()) {
			return target.isOccupiedByOpponent(this);
		}
		return true;  // empty tile is valid
	}

	/**
	 * Calculates moves in a direction, adding valid tiles to the given set.
	 */
	protected void calculateMoves(Set<ChessBoardTile> suggestedMoves, int rowIncrement, int colIncrement) {
		int row = tile.getRow();
		int col = tile.getColumn();

		for (int i = 1; i < 8; i++) {
			ChessBoardTile newTile = board.getTileAt(row + i * rowIncrement, col + i * colIncrement);
			if (newTile == null) break;

			if (moveCheck(newTile)) {
				suggestedMoves.add(newTile);
				if (newTile.isOccupied()) {
					// Cannot jump over occupied tiles
					break;
				}
			} else {
				break;
			}
		}
	}

	/**
	 * Adds static moves based on offsets (used for king, knight).
	 */
	protected void addStaticMoves(Set<ChessBoardTile> suggestedMoves, int[][] moveOffsets, int currentRow, int currentCol) {
		for (int[] offset : moveOffsets) {
			int newRow = currentRow + offset[0];
			int newCol = currentCol + offset[1];
			ChessBoardTile newTile = board.getTileAt(newRow, newCol);

			if (moveCheck(newTile)) {
				suggestedMoves.add(newTile);
			}
		}
	}
}
