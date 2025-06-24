package org.infoLK.figure.types;

import org.infoLK.constants.FigureType;
import org.infoLK.figure.AChessFigure;
import org.infoLK.game.PlayerTeam;
import org.infoLK.gui.board.ChessBoardTile;

import java.util.Set;

public class Queen extends AChessFigure {

	public Queen(ChessBoardTile tile, PlayerTeam team) {
		super(tile, team);
		this.type = FigureType.QUEEN;
	}

	@Override
	public Set<ChessBoardTile> suggestMoves() {
		return Set.of();
	}
}
