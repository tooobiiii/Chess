package org.infoLK.figure;

import lombok.Getter;
import lombok.Setter;
import org.infoLK.constants.FigureType;
import org.infoLK.game.PlayerTeam;
import org.infoLK.gui.board.ChessBoardTile;

import java.util.Set;

@Getter
@Setter
public abstract class AChessFigure {

	protected ChessBoardTile tile;
	protected FigureType type;
	protected PlayerTeam team;

	protected AChessFigure(ChessBoardTile tile, PlayerTeam team) {
		this.tile = tile;
		this.team = team;
	}

	public abstract Set<ChessBoardTile> suggestMoves();

	public void moveFigure(ChessBoardTile target) {
		if (target != null) {
			tile.setFigure(null);
			this.tile = target;
			target.setFigure(this);
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
}
