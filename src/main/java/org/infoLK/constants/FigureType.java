package org.infoLK.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FigureType {

	PAWN("♙"),
	ROOK("♖"),
	KNIGHT("♘"),
	BISHOP("♗"),
	QUEEN("♕"),
	KING("♔");

	private final String symbol;
}