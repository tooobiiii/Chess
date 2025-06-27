package org.infoLK.figure;

import lombok.Getter;
import lombok.Setter;
import org.infoLK.constants.FigureType;
import org.infoLK.game.PlayerTeam;
import org.infoLK.gui.board.ChessBoard;
import org.infoLK.gui.board.ChessBoardTile;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public abstract class AChessFigure {

	protected ChessBoardTile tile;
	protected FigureType type;
	protected PlayerTeam team;
	protected Set<ChessBoardTile> suggestedMoves;

	protected AChessFigure(ChessBoardTile tile, PlayerTeam team) {
		this.tile = tile;
		this.team = team;
		suggestedMoves = new HashSet<>();
	}

	public abstract Set<ChessBoardTile> suggestMoves();

	public void moveFigure(ChessBoardTile target) {
		if (target != null) {
			tile.setFigure(null);
			this.tile = target;
			target.setFigure(this);
			suggestedMoves.clear();
			PlayerTeam.switchTurn();
		}
	}

	protected boolean moveCheck(ChessBoardTile target) {
		if (target == null || target.occupiedByTeam() == PlayerTeam.getCurrentTurn()) {
			return false;
		}
		if (target.isOccupied() && target.isOccupiedByOpponent(this)) {
			return true;
		}
		return !target.isOccupied();
	}

	protected void calculateMoves(Set<ChessBoardTile> suggestedMoves, int rowIncrement, int colIncrement) {
		for (int i = 1; i < 8; i++) {
			ChessBoardTile newTile = ChessBoard.getTileAt(tile.getRow() + i * rowIncrement, tile.getColumn() + i * colIncrement);
			if (moveCheck(newTile)) {
				suggestedMoves.add(newTile);
			} else
				break;
		}
	}

	protected void addStaticMoves(Set<ChessBoardTile> suggestedMoves, int[][] moveOffsets, int currentRow, int currentCol) {
		for (int[] offset : moveOffsets) {
			int newRow = currentRow + offset[0];
			int newCol = currentCol + offset[1];
			ChessBoardTile newTile = ChessBoard.getTileAt(newRow, newCol);

			if (moveCheck(newTile))
				suggestedMoves.add(newTile);
		}
	}
}
