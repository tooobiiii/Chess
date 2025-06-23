package org.infoLK.gui.board;

import javax.swing.*;
import java.awt.*;

public class ChessBoardGraphic extends JFrame {

	private static final int TileSize = 100;

	public ChessBoardGraphic(int rows, int columns) {
		super("InfoLK - Chess");
		setSize(new Dimension(TileSize * rows, TileSize * columns));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
	}
}
