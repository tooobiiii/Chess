package org.infoLK.gui.board.graphic;

import lombok.Getter;
import org.infoLK.constants.Constants;

import javax.swing.*;
import java.awt.*;

@Getter
public class ChessBoardGraphic extends JFrame {

	private final JPanel panel;

	public ChessBoardGraphic(int rows, int columns) {
		super("InfoLK - Chess");
		int TILE_SIZE = Constants.TILE_SIZE;
		int OFFSET = 50;

		setSize(new Dimension(TILE_SIZE * rows, TILE_SIZE * columns));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		panel = new JPanel();
		panel.setLayout(new GridLayout(rows, columns, 0, 0));
		panel.setBorder(BorderFactory.createEmptyBorder(OFFSET, OFFSET, OFFSET, OFFSET));
		panel.setPreferredSize(new Dimension(columns * TILE_SIZE + 2 * OFFSET, rows * TILE_SIZE + 2 * OFFSET));
		add(panel);
		pack();

		setVisible(true);
	}
}
