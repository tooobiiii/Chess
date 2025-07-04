package me.tooobiiii.gui.board.graphic.handler;

import lombok.Getter;
import me.tooobiiii.figure.AChessFigure;
import me.tooobiiii.game.PlayerTeam;
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
	private final static Set<ChessBoardTileGraphic> markedTiles = new HashSet<>();
	private static final Color SELCTION_COLOR = Color.getHSBColor(0.6f, 0.5f, 0.9f);
	private static final Color SUGGESTION_COLOR = Color.getHSBColor(0.33f, 0.3f, 0.85f);
	private static ChessBoardTile selectedTile = null;
	private final ChessBoardTile tile;

	public ChessBoardTileListener(ChessBoardTile tile) {
		this.tile = tile;
	}

	/**
	 * Invoked when the mouse button has been clicked (pressed
	 * and released) on a component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * Invoked when a mouse button has been pressed on a component.
	 *
	 * @param e the event to be processed
	 */
	public void mousePressed(MouseEvent e) {
		if (PlayerTeam.getCurrentTurn() == PlayerTeam.NONE) return;
		if (selectedTile == null)
			handleSelection();
		else {
			if (tile.occupiedByTeam() == PlayerTeam.getCurrentTurn()) {
				clearMarkedTiles();
				handleSelection();
			} else
				handleMove();
		}
	}

	/**
	 * Invoked when a mouse button has been released on a component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * Invoked when the mouse enters a component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseEntered(MouseEvent e) {

	}

	/**
	 * Invoked when the mouse exits a component.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseExited(MouseEvent e) {

	}

	/**
	 * Invoked when a mouse button is pressed on a component and then
	 * dragged.  {@code MOUSE_DRAGGED} events will continue to be
	 * delivered to the component where the drag originated until the
	 * mouse button is released (regardless of whether the mouse position
	 * is within the bounds of the component).
	 * <p>
	 * Due to platform-dependent Drag&amp;Drop implementations,
	 * {@code MOUSE_DRAGGED} events may not be delivered during a native
	 * Drag&amp;Drop operation.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseDragged(MouseEvent e) {

	}

	/**
	 * Invoked when the mouse cursor has been moved onto a component
	 * but no buttons have been pushed.
	 *
	 * @param e the event to be processed
	 */
	@Override
	public void mouseMoved(MouseEvent e) {

	}

	private void handleSelection() {
		if (tile.occupiedByTeam() != PlayerTeam.getCurrentTurn())
			return;
		tile.getGraphic().addHighlight(SELCTION_COLOR);
		markSuggestedTiles(tile.getFigure().suggestMoves());
		selectedTile = tile;
	}

	private void handleMove() {
		AChessFigure figure = selectedTile.getFigure();
		if (!figure.suggestMoves().contains(tile))
			return;
		figure.moveFigure(tile);
		clearMarkedTiles();
		selectedTile = null;
	}

	private void markSuggestedTiles(Set<ChessBoardTile> suggestedMoves) {
		if (suggestedMoves.isEmpty())
			return;
		for (ChessBoardTile tile : suggestedMoves)
			tile.getGraphic().addHighlight(SUGGESTION_COLOR);
	}

	private void clearMarkedTiles() {
		markedTiles.iterator().forEachRemaining(ChessBoardTileGraphic :: removeHighlight);
		markedTiles.clear();
	}
}
