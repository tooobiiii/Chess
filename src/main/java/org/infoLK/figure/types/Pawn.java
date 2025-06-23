package org.infoLK.figure.types;

import org.infoLK.constants.FigureType;
import org.infoLK.figure.AChessFigure;

public class Pawn extends AChessFigure {

	public Pawn() {
		this.type = FigureType.PAWN;
	}

	@Override
	protected void suggestMoves() {

	}
}
