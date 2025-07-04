package me.tooobiiii;

import lombok.Getter;
import me.tooobiiii.gui.LobbyMenu;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ChessClient extends JFrame {

	private static final String SERVER_HOST = "45.152.240.60";
	private static final int SERVER_PORT = 25587;

	private LobbyMenu lobbyMenu;
	@Getter
	private final String username;
	private final Socket socket;
	@Getter
	private final PrintWriter out;
	private final BufferedReader in;

	public ChessClient(String username) throws IOException {
		this.username = username;
		this.socket = new Socket(SERVER_HOST, SERVER_PORT);
		this.out = new PrintWriter(socket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		lobbyMenu = new LobbyMenu(this);
		// Send JOIN
		out.println("JOIN:" + username);

		// Listen for updates
		new Thread(this::listenToServer).start();
	}

	public static void main(String[] args) {
		requestUsername();
	}

	private void listenToServer() {
		try {
			String line;
			while ((line = in.readLine()) != null) {
				String[] parts = line.split(":", 2);
				String command = parts[0];
				String rest = parts.length > 1 ? parts[1] : "";
				switch (command) {
					case "PLAYERS":
						String[] names = rest.split(",");
						SwingUtilities.invokeLater(() -> lobbyMenu.updatePlayerList(Arrays.asList(names)));
						break;
					case "CHALLENGE":
						String challenger = rest;
						SwingUtilities.invokeLater(() -> {
							int result = JOptionPane.showConfirmDialog(
									this,
									challenger + " has challenged you! Accept?",
									"Incoming Challenge",
									JOptionPane.YES_NO_OPTION
							);
							if (result == JOptionPane.YES_OPTION) {
								out.println("CHALLENGE_RESPONSE:" + username + ":" + challenger + ":ACCEPT");
							} else {
								out.println("CHALLENGE_RESPONSE:" + username + ":" + challenger + ":DECLINE");
							}
						});
						break;
					case "CHALLENGE_RESPONSE":
						String[] respParts = rest.split(":");
						String opponent = respParts[0];
						String response = respParts[1];
						SwingUtilities.invokeLater(() -> {
							if (!"ACCEPT".equalsIgnoreCase(response))
								JOptionPane.showMessageDialog(this, opponent + " declined your challenge.");
						});
						break;
					case "GAME_START":
						String[] startParts = rest.split(":");
						String white = startParts[0];
						String black = startParts[1];
						SwingUtilities.invokeLater(() -> {
							JOptionPane.showMessageDialog(this, "Game starting! White: " + white + ", Black: " + black);
							// Launch game window here!
						});
						lobbyMenu.setVisible(false);
						break;
					default:
						System.out.println("Unknown server message: " + line);
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Lost connection to server.", "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("Connection error: " + e.getMessage());
			System.exit(1);
		}
	}



	private static void requestUsername() {
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
