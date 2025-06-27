package org.infoLK.gui.board.graphic;

import org.infoLK.constants.Constants;
import org.infoLK.figure.AChessFigure;
import org.infoLK.gui.board.ChessBoardTile;
import org.infoLK.gui.board.graphic.handler.ChessBoardTileListener;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChessBoardTileGraphic extends JPanel {

	private final ChessBoardTile tile;
	private static final Map<ChessBoardTile, Color> Highlighted = Collections.synchronizedMap(new HashMap<>());

	public ChessBoardTileGraphic(ChessBoardTile tile, JPanel panel) {
		this.tile = tile;
		setPreferredSize(new Dimension(Constants.TILE_SIZE, Constants.TILE_SIZE));
		panel.add(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		paintTileBackground(g);
		paintFigure(g);

		if (Highlighted.containsKey(tile)) {
			g.setColor(Highlighted.get(tile));
			((Graphics2D) g).setStroke(new BasicStroke(7));
			g.drawRect(0, 0, getWidth(), getHeight());
		}
	}

	private void paintTileBackground(Graphics g)
	{
		int row = tile.getRow();
		int col = tile.getColumn();
		boolean isLight = (row + col) % 2 == 0;
		g.setColor(isLight ? Color.LIGHT_GRAY : Color.DARK_GRAY);
		g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);
	}

	private void paintFigure(Graphics g) {
		AChessFigure figure = tile.getFigure();
		if (figure != null) {
			String symbol = figure.getType().getSymbol();
			g.setFont(new Font("SansSerif", Font.PLAIN, g.getClipBounds().height - g.getClipBounds().height / 5));
			g.setColor(figure.getTeam().getColor());
			FontMetrics fm = g.getFontMetrics();
			int x = (g.getClipBounds().width - fm.stringWidth(symbol)) / 2;
			int y = (g.getClipBounds().height + fm.getAscent() - fm.getDescent()) / 2;
			g.drawString(symbol, x, y);
		}
	}

	public void addHighlight(Color color) {
		Highlighted.put(tile, color);
		ChessBoardTileListener.getMarkedTiles().add(this);
		repaint();
	}

	public void removeHighlight() {
		Highlighted.remove(tile);
		repaint();
	}
}
