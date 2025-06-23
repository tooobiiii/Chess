package org.infoLK.figure;

import lombok.Getter;
import org.infoLK.constants.FigureType;

@Getter
public abstract class AChessFigure {

	protected FigureType type;

	protected AChessFigure() {

	}

	protected abstract void suggestMoves();
}
