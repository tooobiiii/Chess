package me.tooobiiii.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

@AllArgsConstructor
public enum PlayerTeam {
	WHITE(Color.WHITE),
	BLACK(Color.BLACK),
	NONE(null);

	@Getter
	private final Color color;

	public static PlayerTeam getOpponent(PlayerTeam team) {
		if (team == WHITE) return BLACK;
		if (team == BLACK) return WHITE;
		return NONE;
	}
}
