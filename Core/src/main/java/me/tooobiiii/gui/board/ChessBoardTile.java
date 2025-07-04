package me.tooobiiii.gui.board;

import lombok.Getter;
import me.tooobiiii.figure.AChessFigure;
import me.tooobiiii.game.ChessGameController;
import me.tooobiiii.game.PlayerTeam;
import me.tooobiiii.gui.board.graphic.ChessBoardTileGraphic;
import me.tooobiiii.gui.board.graphic.handler.ChessBoardTileListener;

import javax.swing.*;

@Getter
public class ChessBoardTile {

	private final int row;
	private final int column;

	private AChessFigure figure = null;

	private final ChessBoardTileGraphic graphic;

	// UPDATED: Accept controller
	public ChessBoardTile(int row, int column, JPanel panel, ChessGameController controller) {
		this.row = row;
		this.column = column;
		this.graphic = new ChessBoardTileGraphic(this, panel);

		ChessBoardTileListener listener = new ChessBoardTileListener(this, controller);
		graphic.addMouseListener(listener);
		graphic.addMouseMotionListener(listener);
	}


	public void setFigure(AChessFigure figure) {
		this.figure = figure;
		graphic.repaint();
	}

	public boolean isOccupied() {
		return figure != null;
	}

	public boolean isOccupiedByOpponent(AChessFigure figure) {
		return isOccupied() && this.figure.getTeam() != figure.getTeam();
	}

	public PlayerTeam occupiedByTeam() {
		return isOccupied() ? figure.getTeam() : PlayerTeam.NONE;
	}
}
