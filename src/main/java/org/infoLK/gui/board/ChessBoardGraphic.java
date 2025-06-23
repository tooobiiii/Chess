package org.infoLK.gui.board;

import javax.swing.*;
import java.awt.*;

public class ChessBoardGraphic extends JFrame {

	public ChessBoardGraphic(int rows, int columns) {
		super("InfoLK - Chess");
		setSize(new Dimension(ChessBoardTile.Size * rows, ChessBoardTile.Size * columns));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
	}
}
