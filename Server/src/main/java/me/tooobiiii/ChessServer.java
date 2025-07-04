package me.tooobiiii;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChessServer {

	private final Set<ClientHandler> clients = ConcurrentHashMap.newKeySet();
	private volatile boolean running = true;

	public ChessServer() {
		System.out.println("Starting Chess Server...");

		Thread commandThread = new Thread(() -> {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
				while (running) {
					System.out.print("> ");
					String command = reader.readLine();
					if ("stop".equalsIgnoreCase(command)) {
						System.out.println("Stopping server...");
						running = false;
						System.exit(0);
					} else {
						System.out.println("Unknown command: " + command);
					}
				}
			} catch (IOException e) {
				System.err.println("Error reading commands: " + e.getMessage());
			}
		});
		commandThread.start();

		try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(System.getenv().getOrDefault("SERVER_PORT", "25587")))) {
			System.out.println("Server is listening on port " + serverSocket.getLocalPort());

			while (running) {
				Socket socket = serverSocket.accept();
				ClientHandler handler = new ClientHandler(socket);
				clients.add(handler);
				new Thread(handler).start();
			}
		} catch (IOException e) {
			System.err.println("Server error: " + e.getMessage());
		}
	}

	private void broadcastPlayers() {
		String playerList = "PLAYERS:" + String.join(",", getUsernames());
		for (ClientHandler client : clients) {
			client.sendMessage(playerList);
		}
	}

	private List<String> getUsernames() {
		return clients.stream()
				.map(c -> c.username)
				.filter(Objects::nonNull)
				.toList();
	}

	private class ClientHandler implements Runnable {
		private final Socket socket;
		private PrintWriter out;
		private BufferedReader in;
		private String username;

		ClientHandler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try (
					socket;
					PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
			) {
				this.out = writer;
				this.in = reader;

				String line;
				while ((line = in.readLine()) != null) {
					if (line.startsWith("JOIN:")) {
						this.username = line.substring(5).trim();
						System.out.println(username + " joined the lobby.");
						broadcastPlayers();
					} else if (line.startsWith("CHALLENGE:")) {
						String[] parts = line.split(":", 3);
						if (parts.length == 3) {
							String from = parts[1];
							String to = parts[2];
							System.out.println(from + " is challenging " + to);
							sendChallengeRequest(from, to);
						}
					}
				}
			} catch (IOException e) {
				System.err.println("Connection error: " + e.getMessage());
			} finally {
				clients.remove(this);
				broadcastPlayers();
				System.out.println("Client disconnected: " + username);
			}
		}

		void sendMessage(String msg) {
			if (out != null) {
				out.println(msg);
			}
		}

		void sendChallengeRequest(String from, String to) {
			for (ClientHandler c : clients) {
				if (c.username != null && c.username.equals(to)) {
					c.sendMessage("CHALLENGE_REQUEST:" + from);
					break;
				}
			}
		}
	}

	public static void main(String[] args) {
		new ChessServer();
	}
}
