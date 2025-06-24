package org.infoLK.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;

@NoArgsConstructor
@AllArgsConstructor
public enum PlayerTeam {

	WHITE(Color.WHITE),
	BLACK(Color.BLACK),
	NONE;

	@Getter
	private static PlayerTeam currentTurn = WHITE;
	@Getter
	private Color color;

	public static void switchTurn() {
		currentTurn = (currentTurn == WHITE) ? BLACK : WHITE;
	}
}
