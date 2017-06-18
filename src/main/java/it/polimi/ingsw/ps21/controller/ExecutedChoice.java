package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class ExecutedChoice extends Message {

	public ExecutedChoice(PlayerColor dest) {
		super(dest);
	}

}
