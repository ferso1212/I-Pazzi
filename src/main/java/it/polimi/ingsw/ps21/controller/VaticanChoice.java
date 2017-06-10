package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class VaticanChoice extends Message{
	
	private boolean chosen;

	public VaticanChoice(PlayerColor dest, String message, boolean chosen) {
		super(dest);
		this.message = "You have to choose to support or not the church (don't do it, Satan is beatiful)";
		this.chosen = chosen;
	}

	public boolean getChosen() {
		return chosen;
	}

	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}

}
