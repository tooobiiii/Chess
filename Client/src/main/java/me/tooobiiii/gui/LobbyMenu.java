package me.tooobiiii.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class LobbyMenu extends JFrame {
	private final String currentPlayer;
	private final DefaultListModel<String> playerListModel;
	private final JPanel playerPanel;
	private final HashMap<String, JButton> challengeButtons;

	public LobbyMenu(String playerName) {
		this.currentPlayer = playerName;
		this.playerListModel = new DefaultListModel<>();
		this.challengeButtons = new HashMap<>();

		setTitle("Chess Lobby - Welcome " + playerName);
		setSize(500, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Styling
		UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));
		UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 16));

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(new Color(30, 30, 30));

		JLabel title = new JLabel("Chess Lobby");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Georgia", Font.BOLD, 26));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		mainPanel.add(title, BorderLayout.NORTH);

		playerPanel = new JPanel();
		playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
		playerPanel.setBackground(new Color(40, 40, 40));

		JScrollPane scrollPane = new JScrollPane(playerPanel);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		add(mainPanel);
		setVisible(true);

		// Add self to lobby
		addPlayer(playerName);
	}

	public void addPlayer(String name) {
		if (playerListModel.contains(name)) return;
		playerListModel.addElement(name);

		JPanel playerEntry = new JPanel(new BorderLayout());
		playerEntry.setMaximumSize(new Dimension(400, 50));
		playerEntry.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		playerEntry.setBackground(name.equals(currentPlayer) ? new Color(60, 100, 60) : new Color(60, 60, 60));

		JLabel nameLabel = new JLabel(name);
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
		playerEntry.add(nameLabel, BorderLayout.WEST);

		if (!name.equals(currentPlayer)) {
			JButton challengeButton = new JButton("Challenge");
			challengeButton.setFocusPainted(false);
			challengeButton.setBackground(new Color(80, 80, 200));
			challengeButton.setForeground(Color.WHITE);
			challengeButton.addActionListener((ActionEvent e) -> challengePlayer(name));
			playerEntry.add(challengeButton, BorderLayout.EAST);
			challengeButtons.put(name, challengeButton);
		}

		playerPanel.add(playerEntry);
		playerPanel.revalidate();
		playerPanel.repaint();
	}

	private void challengePlayer(String opponent) {
		// You will handle the actual game initiation
		JOptionPane.showMessageDialog(this, "You challenged " + opponent + "!", "Challenge Sent", JOptionPane.INFORMATION_MESSAGE);
		// Call your chess initiation logic here
	}
}