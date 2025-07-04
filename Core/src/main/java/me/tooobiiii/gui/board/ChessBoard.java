package me.tooobiiii.gui.board;

import lombok.Getter;
import lombok.NonNull;
import me.tooobiiii.constants.Constants;
import me.tooobiiii.figure.AChessFigure;
import me.tooobiiii.figure.types.*;
import me.tooobiiii.game.ChessGameController;
import me.tooobiiii.game.PlayerTeam;
import me.tooobiiii.gui.board.graphic.ChessBoardGraphic;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ChessBoard {

	private static final int ROWS = Constants.ROWS;
	private static final int COLUMNS = Constants.COLUMNS;

	private final ChessBoardTile[][] chessBoard = new ChessBoardTile[ROWS][COLUMNS];
	private final Set<AChessFigure> whiteFigures = new LinkedHashSet<>();
	private final Set<AChessFigure> blackFigures = new LinkedHashSet<>();

	@Getter
	private final ChessBoardGraphic graphic;

	@Getter
	private final ChessGameController controller;

	public ChessBoard(ChessGameController controller) {
		this.controller = controller;
		this.graphic = new ChessBoardGraphic(ROWS, COLUMNS);
		initBoard();
		placeStartingFigures();
	}

	public ChessBoardTile getTileAt(int row, int col) {
		if (row >= 0 && row < ROWS && col >= 0 && col < COLUMNS) {
			return chessBoard[row][col];
		}
		return null;
	}

	public boolean isCheckMate(PlayerTeam team) {
		Set<AChessFigure> opponentFigures = (team == PlayerTeam.WHITE) ? blackFigures : whiteFigures;
		King king = getTeamKing(team);
		if (king == null) return false;

		for (AChessFigure figure : opponentFigures) {
			if (figure.suggestMoves().contains(king.getTile()) && king.suggestMoves().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public @NonNull King getTeamKing(PlayerTeam team) {
		Set<AChessFigure> figures = getFiguresByTeam(team);
		for (AChessFigure figure : figures) {
			if (figure instanceof King) {
				return (King) figure;
			}
		}
		throw new IllegalStateException("No king found for team: " + team);
	}

	public Set<AChessFigure> getFiguresByTeam(PlayerTeam team) {
		return (team == PlayerTeam.WHITE) ? whiteFigures : blackFigures;
	}

	private void initBoard() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				chessBoard[row][col] = new ChessBoardTile(row, col, graphic.getPanel(), controller);
			}
		}
	}

	private void placeStartingFigures() {
		List<Class<? extends AChessFigure>> pieceOrder = Arrays.asList(
				Rook.class, Knight.class, Bishop.class, Queen.class, King.class, Bishop.class, Knight.class, Rook.class
		);

		for (int i = 0; i < 2; i++) {
			int mainRow = (i == 0) ? 0 : 7;
			int pawnRow = (i == 0) ? 1 : 6;
			PlayerTeam team = (i == 0) ? PlayerTeam.BLACK : PlayerTeam.WHITE;

			// Main pieces
			for (int col = 0; col < 8; col++) {
				ChessBoardTile tile = chessBoard[mainRow][col];
				try {
					AChessFigure piece = pieceOrder.get(col)
							.getConstructor(ChessBoard.class, ChessBoardTile.class, PlayerTeam.class)
							.newInstance(this, tile, team);
					tile.setFigure(piece);
					addFigure(piece);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// Pawns
			for (int col = 0; col < 8; col++) {
				ChessBoardTile tile = chessBoard[pawnRow][col];
				Pawn pawn = new Pawn(this, tile, team);
				tile.setFigure(pawn);
				addFigure(pawn);
			}
		}
	}

	public void addFigure(AChessFigure figure) {
		if (figure.getTeam() == PlayerTeam.WHITE) {
			whiteFigures.add(figure);
		} else {
			blackFigures.add(figure);
		}
	}

	public void removeFigure(AChessFigure figure) {
		if (figure.getTeam() == PlayerTeam.WHITE) {
			whiteFigures.remove(figure);
		} else {
			blackFigures.remove(figure);
		}
	}
}