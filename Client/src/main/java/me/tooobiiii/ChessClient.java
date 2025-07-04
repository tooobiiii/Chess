package me.tooobiiii;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;

public class ChessClient extends JFrame {

	private static final String SERVER_HOST = "45.152.240.60";
	private static final int SERVER_PORT = 25587;

	private final String username;
	private final Socket socket;
	private final PrintWriter out;
	private final BufferedReader in;
	private final JPanel playerPanel;
	private final Set<String> players = new HashSet<>();

	public ChessClient(String username) throws IOException {
		this.username = username;
		this.socket = new Socket(SERVER_HOST, SERVER_PORT);
		this.out = new PrintWriter(socket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

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

		// Send JOIN
		out.println("JOIN:" + username);

		// Listen for updates
		new Thread(this::listenToServer).start();
	}

	private void listenToServer() {
		try {
			String line;
			while ((line = in.readLine()) != null) {
				if (line.startsWith("PLAYERS:")) {
					String[] names = line.substring(8).split(",");
					SwingUtilities.invokeLater(() -> updatePlayerList(Arrays.asList(names)));
				} else if (line.startsWith("CHALLENGE_REQUEST:")) {
					String challenger = line.substring("CHALLENGE_REQUEST:".length());
					SwingUtilities.invokeLater(() -> {
						JOptionPane.showMessageDialog(this,
								"You were challenged by " + challenger + "!",
								"Incoming Challenge",
								JOptionPane.INFORMATION_MESSAGE);
					});
				}
			}
		} catch (IOException e) {
			showError("Lost connection to server.");
		}
	}

	private void updatePlayerList(List<String> newPlayers) {
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
		out.println("CHALLENGE:" + username + ":" + opponent);
	}

	private void showError(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
		System.exit(1);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			String username = JOptionPane.showInputDialog(null, "Enter your username:");
			if (username == null || username.trim().isEmpty()) return;

			try {
				new ChessClient(username.trim());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Could not connect to server:\n" + e.getMessage(),
						"Connection Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}
