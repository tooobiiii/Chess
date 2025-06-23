package org.infoLK.figure.types;

import org.infoLK.constants.FigureType;
import org.infoLK.figure.AChessFigure;

public final class Bishop extends AChessFigure {

	public Bishop() {
		this.type = FigureType.BISHOP;
	}

	@Override
	protected void suggestMoves() {
	}
}
