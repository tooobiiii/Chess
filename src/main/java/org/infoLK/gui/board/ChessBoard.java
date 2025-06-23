package org.infoLK.gui.board;

import org.infoLK.figure.AChessFigure;

public class ChessBoard {

	private static final int Rows = 8;
	private static final int Columns = 8;

	private final AChessFigure[][] chessBoard;
	private final ChessBoardGraphic graphic;

	public ChessBoard() {
		chessBoard = new AChessFigure[Rows][Columns];
		graphic = new ChessBoardGraphic(Rows, Columns);
	}
}
