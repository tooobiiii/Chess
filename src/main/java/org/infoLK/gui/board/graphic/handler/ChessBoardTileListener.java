package org.infoLK.gui.board.graphic.handler;

import org.infoLK.game.PlayerTeam;
import org.infoLK.gui.board.ChessBoardTile;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Set;

public class ChessBoardTileListener implements MouseListener, MouseMotionListener {

	private static ChessBoardTile selectedTile = null;
	private final ChessBoardTile tile;
	private static Set<ChessBoardTile> suggestedMoves = new HashSet<>();

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
	@Override
	public void mousePressed(MouseEvent e) {
//		if (selectedTile != null)
//			selectedTile.getGraphic().removeHighlight();
//
//		if (tile.occupiedByTeam() != PlayerTeam.getCurrentTurn() && !selectedTile.isOccupied())
//			return;
//
//		if (handleTryMove()) return;
//
//		tile.getGraphic().addHighlight();
//
//		selectedTile = tile;
//		suggestedMoves = suggestMoves();

		if (selectedTile == null) {
			if (tile.occupiedByTeam() != PlayerTeam.getCurrentTurn())
				return;
			tile.getGraphic().addHighlight();
			selectedTile = tile;
			suggestedMoves = selectedTile.getFigure().suggestMoves();
		} else {
			if (tile.occupiedByTeam() == PlayerTeam.getCurrentTurn()) {
				selectedTile.getGraphic().removeHighlight();
				tile.getGraphic().addHighlight();
				selectedTile = tile;
				suggestedMoves = selectedTile.getFigure().suggestMoves();
				return;
			}

			if (suggestedMoves.isEmpty() || !suggestedMoves.contains(tile)) {
				System.out.println("Der move war laut Berechnung illegal!");
			}
			selectedTile.getFigure().moveFigure(tile);
			selectedTile.getGraphic().removeHighlight();
			selectedTile = null;
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

	private boolean handleTryMove() {
		if (selectedTile == null || (!selectedTile.isOccupied() || selectedTile.getFigure().getTeam() != PlayerTeam.getCurrentTurn()))
			return false;
		if (!tile.isOccupied()) {
			selectedTile.getFigure().moveFigure(tile);
			PlayerTeam.switchTurn();
			return true;
		}

		return false;
	}
}
