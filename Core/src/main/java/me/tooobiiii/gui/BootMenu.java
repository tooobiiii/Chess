package me.tooobiiii.gui;

import me.tooobiiii.gui.board.ChessBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class BootMenu extends JFrame {

	public BootMenu() {
		super("InfoLK - Chess");
		setSize(new Dimension(400, 100));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		panel.add(createButton("Load", (ActionEvent e) -> JOptionPane.showMessageDialog(this, "Load clicked, not implemented yet!")));

		panel.add(createButton("Start", (ActionEvent e) -> {
			dispose();
		}));

		panel.add(createButton("Exit", (ActionEvent e) -> dispose()));

		add(panel);

		setVisible(true);
	}

	private JButton createButton(String label, ActionListener listener) {
		JButton button = new JButton(label);
		button.addActionListener(listener);
		return button;
	}
}
