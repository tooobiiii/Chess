package me.tooobiiii.network;

import me.tooobiiii.game.ChessGameController;

import java.io.PrintWriter;

public class ChessNetworkAdapter implements ChessGameController.NetworkInterface {
	private final PrintWriter out;
	private ChessGameController.MoveListener moveListener;

	public ChessNetworkAdapter(PrintWriter out) {
		this.out = out;
	}

	@Override
	public void sendMove(int fromRow, int fromCol, int toRow, int toCol) {
		out.println("MOVE:" + fromRow + ":" + fromCol + ":" + toRow + ":" + toCol);
	}

	@Override
	public void setMoveListener(ChessGameController.MoveListener listener) {
		this.moveListener = listener;
	}

	// Call this from your socket listener when you get a MOVE from server:
	public void onMoveFromServer(int fromRow, int fromCol, int toRow, int toCol) {
		if (moveListener != null) moveListener.onMoveReceived(fromRow, fromCol, toRow, toCol);
	}
}
