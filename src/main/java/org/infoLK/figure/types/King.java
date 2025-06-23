package org.infoLK.figure.types;

import org.infoLK.constants.FigureType;
import org.infoLK.figure.AChessFigure;

public class King extends AChessFigure {

	public King() {
		this.type = FigureType.KING;
	}

	@Override
	protected void suggestMoves() {

	}
}
