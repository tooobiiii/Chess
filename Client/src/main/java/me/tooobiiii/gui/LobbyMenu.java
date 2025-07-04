package me.tooobiiii.gui;

import me.tooobiiii.ChessClient;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LobbyMenu extends JFrame {

	private final ChessClient client;
	private final String username;
	private final JPanel playerPanel;
	private final Set<String> players = new HashSet<>();

	public LobbyMenu(ChessClient client) {
		this.client = client;
		this.username = client.getUsername();
		setTitle("Chess Lobby - " + username);
		setSize(500, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Layout
		JPanel main = new JPanel(new BorderLayout());
		main.setBackground(new Color(30, 30, 30));

		JLabel title = new JLabel("Lobby", SwingConstants.CENTER);
		title.setFont(new Font("Serif", Font.BOLD, 26));
		title.setForeground(Color.WHITE);
		title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
		main.add(title, BorderLayout.NORTH);

		playerPanel = new JPanel();
		playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
		playerPanel.setBackground(new Color(40, 40, 40));
		JScrollPane scroll = new JScrollPane(playerPanel);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		main.add(scroll, BorderLayout.CENTER);

		add(main);
		setVisible(true);
	}

	public void updatePlayerList(List<String> newPlayers) {
		players.clear();
		players.addAll(newPlayers);

		playerPanel.removeAll();
		for (String name : newPlayers) {
			JPanel row = new JPanel(new BorderLayout());
			row.setMaximumSize(new Dimension(400, 50));
			row.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			row.setBackground(name.equals(username) ? new Color(60, 100, 60) : new Color(60, 60, 60));

			JLabel label = new JLabel(name);
			label.setForeground(Color.WHITE);
			label.setFont(new Font("Arial", Font.BOLD, 16));
			row.add(label, BorderLayout.WEST);

			if (!name.equals(username)) {
				JButton challengeBtn = new JButton("Challenge");
				challengeBtn.setBackground(new Color(100, 100, 255));
				challengeBtn.setForeground(Color.WHITE);
				challengeBtn.addActionListener(e -> sendChallenge(name));
				row.add(challengeBtn, BorderLayout.EAST);
			}

			playerPanel.add(row);
		}

		playerPanel.revalidate();
		playerPanel.repaint();
	}

	private void sendChallenge(String opponent) {
		client.getOut().println("CHALLENGE:" + username + ":" + opponent);
	}
}
