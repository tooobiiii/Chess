package me.tooobiiii;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChessServer {

	private final Set<ClientHandler> clients = ConcurrentHashMap.newKeySet();
	private final Map<String, ClientHandler> playerMap = new ConcurrentHashMap<>();
	private final Map<String, String> inGame = new ConcurrentHashMap<>();
	private volatile boolean running = true;

	public static void main(String[] args) {
		new ChessServer().start(Integer.parseInt(System.getenv().getOrDefault("SERVER_PORT", "25587")));
	}

	public void start(int port) {
		System.out.println("Starting Chess Server...");

		// Admin command thread
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
		commandThread.setDaemon(true);
		commandThread.start();

		try (ServerSocket serverSocket = new ServerSocket(port)) {
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

	private void broadcastLobbyList() {
		StringBuilder sb = new StringBuilder("PLAYERS:");
		for (String name : playerMap.keySet()) {
			if (!inGame.containsKey(name)) sb.append(name).append(",");
		}
		if (sb.length() > 8 && sb.charAt(sb.length() - 1) == ',') sb.setLength(sb.length() - 1);
		String msg = sb.toString();
		for (ClientHandler c : clients) {
			c.send(msg);
		}
	}

	class ClientHandler implements Runnable {
		private final Socket socket;
		private PrintWriter out;
		private BufferedReader in;
		private String playerName = null;

		ClientHandler(Socket socket) {
			this.socket = socket;
		}

		public void send(String msg) {
			if (out != null) out.println(msg);
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
					String[] parts = line.split(":");
					switch (parts[0]) {
						case "JOIN": // JOIN:playerName
							this.playerName = parts[1];
							playerMap.put(playerName, this);
							System.out.println(playerName + " joined the lobby.");
							broadcastLobbyList();
							break;
						case "CHALLENGE": // CHALLENGE:from:to
							String challenger = parts[1];
							String opponent = parts[2];
							if (inGame.containsKey(challenger) || inGame.containsKey(opponent)) {
								send("INFO:One of the players is already in a game.");
								break;
							}
							System.out.println(challenger + " challenges " + opponent);
							ClientHandler target = playerMap.get(opponent);
							if (target != null) target.send("CHALLENGE:" + challenger);
							break;
						case "CHALLENGE_RESPONSE": // CHALLENGE_RESPONSE:from:to:ACCEPT/DECLINE
							String responder = parts[1];
							String challengerName = parts[2];
							String response = parts[3];
							ClientHandler challengerHandler = playerMap.get(challengerName);
							if (challengerHandler != null)
								challengerHandler.send("CHALLENGE_RESPONSE:" + responder + ":" + response);
							if ("ACCEPT".equals(response)) {
								playerMap.get(challengerName).send("GAME_START:" + challengerName + ":" + responder);
								playerMap.get(responder).send("GAME_START:" + challengerName + ":" + responder);
								inGame.put(challengerName, responder);
								inGame.put(responder, challengerName);
								broadcastLobbyList();
							}
							break;
						case "MOVE": // MOVE:fromRow:fromCol:toRow:toCol
							if (playerName == null) break;
							String opp = inGame.get(playerName);
							if (opp != null && playerMap.containsKey(opp)) {
								playerMap.get(opp).send(line); // relay move
							}
							break;
						case "RESIGN":
							String winner = inGame.get(playerName);
							if (winner != null && playerMap.containsKey(winner)) {
								playerMap.get(winner).send("RESIGN:" + playerName);
								inGame.remove(winner);
							}
							inGame.remove(playerName);
							broadcastLobbyList();
							break;
						default:
							send("INFO:Unknown command: " + line);
					}
				}
			} catch (IOException e) {
				System.err.println("Connection error for " + playerName + ": " + e.getMessage());
			} finally {
				clients.remove(this);
				if (playerName != null) {
					playerMap.remove(playerName);
					inGame.remove(playerName);
					String opponent = inGame.entrySet().stream()
							.filter(entry -> entry.getValue().equals(playerName))
							.map(Map.Entry::getKey).findFirst().orElse(null);
					if (opponent != null) inGame.remove(opponent);
				}
				broadcastLobbyList();
				System.out.println("Client disconnected: " + playerName);
			}
		}
	}
}
