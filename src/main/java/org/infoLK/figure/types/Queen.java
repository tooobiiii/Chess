package org.infoLK.figure.types;

import org.infoLK.constants.FigureType;
import org.infoLK.figure.AChessFigure;

public class Queen extends AChessFigure {

	public Queen() {
		this.type = FigureType.QUEEN;
	}

	@Override
	protected void suggestMoves() {

	}
}
