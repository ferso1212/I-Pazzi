package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class RefusedAction extends Message{
	
	public RefusedAction(PlayerColor destination) {
		super(destination);
		this.message = "You can't do this action!";
	}

	public RefusedAction(PlayerColor destination, String message) {
		super(destination);
		this.message = message;
	}
	
	
	
}
