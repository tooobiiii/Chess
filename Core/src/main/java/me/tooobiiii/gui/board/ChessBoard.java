package me.tooobiiii.gui.board;


import lombok.Getter;
import lombok.NonNull;
import me.tooobiiii.constants.Constants;
import me.tooobiiii.figure.AChessFigure;
import me.tooobiiii.figure.types.*;
import me.tooobiiii.game.PlayerTeam;
import me.tooobiiii.gui.board.graphic.ChessBoardGraphic;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ChessBoard {

	private static final int Rows = Constants.ROWS;
	private static final int Columns = Constants.COLUMNS;
	private static final ChessBoardTile[][] chessBoard = new ChessBoardTile[Rows][Columns];
	private static final Set<AChessFigure> whiteFigures = new LinkedHashSet<>();
	private static final Set<AChessFigure> blackFigures = new LinkedHashSet<>();
	@Getter
	private static final ChessBoardGraphic graphic = new ChessBoardGraphic(Rows, Columns);

	public ChessBoard() {
		initBoard();
		placeStartingFigures();
	}

	public static ChessBoardTile getTileAt(int row, int col) {
		return (row >= 0 && row < Rows && col >= 0 && col < Columns) ? chessBoard[row][col] : null;
	}

	public static boolean isCheckMate(PlayerTeam team) {
		Set<AChessFigure> opponentFigures = team == PlayerTeam.WHITE ? blackFigures : whiteFigures;
		King king = getTeamKing(team);

		for (AChessFigure figure : opponentFigures) {
			if (figure.suggestMoves().contains(king.getTile()) && king.suggestMoves().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public static @NonNull King getTeamKing(PlayerTeam team) {
		Set<AChessFigure> figures = getFiguresByTeam(team);
		for (AChessFigure figure : figures) {
			if (figure instanceof King) {
				return (King) figure;
			}
		}
		return null;
	}

	public static Set<AChessFigure> getFiguresByTeam(PlayerTeam team) {
		return team == PlayerTeam.WHITE ? whiteFigures : blackFigures;
	}

	private void initBoard() {
		for (int row = 0; row < Rows; row++) {
			for (int col = 0; col < Columns; col++) {
				chessBoard[row][col] = new ChessBoardTile(row, col, graphic.getPanel());
			}
		}
	}

	private void placeStartingFigures() {
		List<Class<? extends AChessFigure>> pieceOrder = Arrays.asList(Rook.class, Knight.class, Bishop.class, Queen.class, King.class, Bishop.class, Knight.class, Rook.class);

		for (int i = 0; i < 2; i++) {
			int mainRow = i == 0 ? 0 : 7;
			int pawnRow = i == 0 ? 1 : 6;
			PlayerTeam team = i == 0 ? PlayerTeam.BLACK : PlayerTeam.WHITE;
			ChessBoardTile tile;

			// Main pieces
			for (int col = 0; col < 8; col++) {
				tile = chessBoard[mainRow][col];
				try {
					AChessFigure piece = pieceOrder.get(col).getConstructor(ChessBoardTile.class, PlayerTeam.class).newInstance(tile, team);
					tile.setFigure(piece);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// Pawns
			for (int col = 0; col < 8; col++) {
				tile = chessBoard[pawnRow][col];
				tile.setFigure(new Pawn(tile, team));
			}
		}
	}
}
