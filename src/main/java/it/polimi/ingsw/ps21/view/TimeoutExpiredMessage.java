package it.polimi.ingsw.ps21.view;

import it.polimi.ingsw.ps21.controller.Message;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class TimeoutExpiredMessage extends Message{

	public TimeoutExpiredMessage(PlayerColor dest, int actionId) {
		super(dest, actionId);
		this.message="Action timeout expired!";
	}

}
