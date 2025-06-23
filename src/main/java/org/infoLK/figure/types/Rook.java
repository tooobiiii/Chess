package org.infoLK.figure.types;

import org.infoLK.constants.FigureType;
import org.infoLK.figure.AChessFigure;

public class Rook extends AChessFigure {

	public Rook() {
		this.type = FigureType.ROOK;
	}

	@Override
	protected void suggestMoves() {
	}
}
