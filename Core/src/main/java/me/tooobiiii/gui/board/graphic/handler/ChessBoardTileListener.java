package me.tooobiiii.gui.board.graphic.handler;

import lombok.Getter;
import me.tooobiiii.figure.AChessFigure;
import me.tooobiiii.game.ChessGameController;
import me.tooobiiii.gui.board.ChessBoardTile;
import me.tooobiiii.gui.board.graphic.ChessBoardTileGraphic;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Set;

public class ChessBoardTileListener implements MouseListener, MouseMotionListener {

	@Getter
	private static final Set<ChessBoardTileGraphic> markedTiles = new HashSet<>();
	private static final Color SELECTION_COLOR = Color.getHSBColor(0.6f, 0.5f, 0.9f);
	private static final Color SUGGESTION_COLOR = Color.getHSBColor(0.33f, 0.3f, 0.85f);
	private static ChessBoardTile selectedTile = null;
	private final ChessBoardTile tile;
	private final ChessGameController controller;

	public ChessBoardTileListener(ChessBoardTile tile, ChessGameController controller) {
		this.tile = tile;
		this.controller = controller;
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		// Only allow input if it's this client's turn (controller knows who you are)
		if (!controller.isMyTurn()) return;

		if (selectedTile == null) {
			handleSelection();
		} else {
			if (tile.occupiedByTeam() == controller.getCurrentTurn()) {
				clearMarkedTiles();
				handleSelection();
			} else {
				handleMove();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	private void handleSelection() {
		if (tile.occupiedByTeam() != controller.getCurrentTurn())
			return;
		tile.getGraphic().addHighlight(SELECTION_COLOR);
		markSuggestedTiles(tile.getFigure().suggestMoves());
		selectedTile = tile;
	}

	private void handleMove() {
		AChessFigure figure = selectedTile.getFigure();
		if (figure == null || !figure.suggestMoves().contains(tile)) {
			selectedTile = null;  // Reset selection on invalid move
			clearMarkedTiles();
			return;
		}

		// Delegate move to controller (validation, networking, etc)
		controller.attemptMove(figure, tile);

		clearMarkedTiles();
		selectedTile = null;
	}

	private void markSuggestedTiles(Set<ChessBoardTile> suggestedMoves) {
		if (suggestedMoves == null || suggestedMoves.isEmpty())
			return;
		for (ChessBoardTile t : suggestedMoves) {
			t.getGraphic().addHighlight(SUGGESTION_COLOR);
			markedTiles.add(t.getGraphic());
		}
	}

	private void clearMarkedTiles() {
		for (ChessBoardTileGraphic graphic : markedTiles) {
			graphic.removeHighlight();
		}
		markedTiles.clear();
	}
}
