package me.tooobiiii.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ChallengeWindow extends JFrame {

	public ChallengeWindow(String challenger, Runnable onAccept, Runnable onDecline) {
		setTitle("Challenge Request");
		setSize(300, 150);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		JLabel messageLabel = new JLabel(challenger + " has challenged you to a game!", SwingConstants.CENTER);
		add(messageLabel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		JButton acceptButton = new JButton("Accept");
		acceptButton.addActionListener((ActionEvent e) -> {
			onAccept.run();
			dispose();
		});

		JButton declineButton = new JButton("Decline");
		declineButton.addActionListener((ActionEvent e) -> {
			onDecline.run();
			dispose();
		});

		buttonPanel.add(acceptButton);
		buttonPanel.add(declineButton);

		add(buttonPanel, BorderLayout.SOUTH);

		setVisible(true);
	}
}