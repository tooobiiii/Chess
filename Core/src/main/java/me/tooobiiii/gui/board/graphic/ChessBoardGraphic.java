package me.tooobiiii.gui.board.graphic;

import lombok.Getter;
import me.tooobiiii.constants.Constants;
import me.tooobiiii.game.PlayerTeam;

import javax.swing.*;
import java.awt.*;

@Getter
public class ChessBoardGraphic extends JFrame {

	private static final int TILE_SIZE = Constants.TILE_SIZE;
	private static final int OFFSET = 50;

	private final JPanel panel;
	private final JLabel turnLabel = new JLabel("Current Turn: ", JLabel.CENTER);

	public ChessBoardGraphic(int rows, int columns) {
		super("InfoLK - Chess");
		configureWindow(rows, columns);
		panel = createPanel(rows, columns);
		add(panel);
		pack();
		setVisible(true);
	}

	private void configureWindow(int rows, int columns) {
		setSize(new Dimension(TILE_SIZE * rows, TILE_SIZE * columns));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitleFont();
	}

	private void setTitleFont() {
		JPanel titlePanel = new JPanel(new BorderLayout());
		JLabel titleLabel = new JLabel(getTitle(), JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setForeground(Color.DARK_GRAY);

		turnLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		turnLabel.setForeground(Color.GRAY);

		titlePanel.add(titleLabel, BorderLayout.NORTH);
		titlePanel.add(turnLabel, BorderLayout.SOUTH);

		setTitle("");
		add(titlePanel, BorderLayout.NORTH);
	}

	private JPanel createPanel(int rows, int columns) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(rows, columns, 0, 0));
		panel.setBorder(BorderFactory.createEmptyBorder((int) (OFFSET * 0.5), OFFSET, OFFSET, OFFSET));
		panel.setPreferredSize(new Dimension(columns * TILE_SIZE + 2 * OFFSET, rows * TILE_SIZE + 2 * OFFSET));
		panel.setBackground(new Color(240, 240, 240)); // Light gray background
		return panel;
	}

	// UPDATED: Let anyone set the turn label to anything (not just current turn)
	public void setTurnLabel(String text) {
		turnLabel.setText(text);
	}

	// (Optional) Redraw/refresh board
	public void refresh() {
		panel.repaint();
		turnLabel.repaint();
	}

	// (Optional) Clear the board
	public void clearBoard() {
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
	}
}
