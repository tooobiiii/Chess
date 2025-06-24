package org.infoLK.figure.types;

import org.infoLK.constants.FigureType;
import org.infoLK.figure.AChessFigure;
import org.infoLK.game.PlayerTeam;
import org.infoLK.gui.board.ChessBoardTile;

import java.util.Set;

public class Pawn extends AChessFigure {

	public Pawn(ChessBoardTile tile, PlayerTeam team) {
		super(tile, team);
		this.type = FigureType.PAWN;
	}

	@Override
	public Set<ChessBoardTile> suggestMoves() {
		return Set.of();
	}
}
