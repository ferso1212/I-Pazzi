package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class AcceptedAction extends Message {


	public AcceptedAction(PlayerColor destination, int id) {
		super(destination, id);
		this.message = "This action is executable";
	}

	

}
