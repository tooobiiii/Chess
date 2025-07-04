package me.tooobiiii.game;

import lombok.Getter;
import lombok.Setter;
import me.tooobiiii.figure.AChessFigure;
import me.tooobiiii.gui.board.ChessBoard;
import me.tooobiiii.gui.board.ChessBoardTile;

import java.util.function.Consumer;

public class ChessGameController {

	private final ChessBoard board;
	@Getter
	private PlayerTeam currentTurn;
	@Getter
	private boolean gameActive;

	// TODO: For real multiplayer, set this to WHITE or BLACK depending on which player we are
	@Getter
	private PlayerTeam localPlayerTeam = PlayerTeam.WHITE;

	@Setter
	private Consumer<String> onStatusUpdate;

	private final NetworkInterface network;

	public ChessGameController(NetworkInterface network) {
		this.board = new ChessBoard(this);
		this.network = network;
		this.currentTurn = PlayerTeam.WHITE;
		this.gameActive = true;

		network.setMoveListener(this :: onRemoteMove);
	}

	/**
	 * Returns true if it's the local player's turn.
	 */
	public boolean isMyTurn() {
		return localPlayerTeam == currentTurn && gameActive;
	}

	public void attemptMove(AChessFigure figure, ChessBoardTile target) {
		if (!gameActive) {
			updateStatus("Game is over.");
			return;
		}
		if (!isMyTurn()) {
			updateStatus("It's not your turn.");
			return;
		}
		if (figure.getTeam() != currentTurn) {
			updateStatus("It's not your turn.");
			return;
		}
		if (!figure.suggestMoves().contains(target)) {
			updateStatus("Invalid move.");
			return;
		}
		// Move is valid locally — update board model
		figure.moveFigure(target);

		// Check for endgame condition
		if (board.isCheckMate(PlayerTeam.getOpponent(currentTurn))) {
			endGame(currentTurn);
			return;
		}

		// Notify GUI and update turn
		switchTurn();

		// Send move to server/network for opponents
		network.sendMove(figure.getTile().getRow(), figure.getTile().getColumn(), target.getRow(), target.getColumn());
	}

	private void onRemoteMove(int fromRow, int fromCol, int toRow, int toCol) {
		if (!gameActive)
			return;
		ChessBoardTile fromTile = board.getTileAt(fromRow, fromCol);
		ChessBoardTile toTile = board.getTileAt(toRow, toCol);

		if (fromTile == null || toTile == null)
			return;
		AChessFigure figure = fromTile.getFigure();
		if (figure == null)
			return;
		if (!figure.suggestMoves().contains(toTile)) {
			// Invalid move received - handle error or ignore
			return;
		}
		// Execute the move locally (no network send needed here)
		figure.moveFigure(toTile);

		// Check for endgame condition
		if (board.isCheckMate(PlayerTeam.getOpponent(currentTurn))) {
			endGame(currentTurn);
			return;
		}
		// Switch turn to local player after opponent move
		switchTurn();
	}

	public void switchTurn() {
		currentTurn = PlayerTeam.getOpponent(currentTurn);
		updateStatus(currentTurn + "'s turn");
	}

	public void updateStatus(String message) {
		if (onStatusUpdate != null) {
			onStatusUpdate.accept(message);
		}
	}

	public void endGame(PlayerTeam winner) {
		gameActive = false;
		updateStatus(winner + " wins!");
	}

	// --- Reset or start a new game ---
	public void resetGame() {
		// (Re-init board and figures if you support restart)
		this.currentTurn = PlayerTeam.WHITE;
		this.gameActive = true;
		updateStatus(currentTurn + "'s turn");
	}

	public interface NetworkInterface {
		void sendMove(int fromRow, int fromCol, int toRow, int toCol);

		void setMoveListener(MoveListener listener);
	}

	@FunctionalInterface
	public interface MoveListener {
		void onMoveReceived(int fromRow, int fromCol, int toRow, int toCol);
	}
}