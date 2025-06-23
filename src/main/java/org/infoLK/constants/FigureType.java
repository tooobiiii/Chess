package org.infoLK.constants;

import lombok.Getter;

@Getter
public enum FigureType {

	PAWN("♙"),
	ROOK("♖"),
	KNIGHT("♘"),
	BISHOP("♗"),
	QUEEN("♕"),
	KING("♔");

	private final String symbol;

	FigureType(String symbol) {
		this.symbol = symbol;
	}
}