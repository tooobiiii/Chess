package me.tooobiiii.gui.board;

import lombok.Getter;
import me.tooobiiii.figure.AChessFigure;
import me.tooobiiii.game.PlayerTeam;
import me.tooobiiii.gui.board.graphic.ChessBoardTileGraphic;
import me.tooobiiii.gui.board.graphic.handler.ChessBoardTileListener;

import javax.swing.*;

@Getter
public class ChessBoardTile {

	private final int row;
	private final int column;

	private boolean isOccupied = false;

	private AChessFigure figure = null;

	private final ChessBoardTileGraphic graphic;

	public ChessBoardTile(int row, int column, JPanel panel) {
		this.row = row;
		this.column = column;
		graphic = new ChessBoardTileGraphic(this, panel);

		graphic.addMouseListener(new ChessBoardTileListener(this));
	}

	public void setFigure(AChessFigure figure) {
		this.figure = figure;
		isOccupied = figure != null;
		graphic.repaint();
	}

	public boolean isOccupiedByOpponent(AChessFigure figure) {
		return isOccupied && this.figure.getTeam() != figure.getTeam();
	}

	public PlayerTeam occupiedByTeam() {
		return isOccupied ? figure.getTeam() : PlayerTeam.NONE;
	}
}
