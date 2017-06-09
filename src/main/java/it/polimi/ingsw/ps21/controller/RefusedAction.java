package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class RefusedAction extends Message{

	private String problem;
	
	public RefusedAction(PlayerColor destination) {
		super(destination);
		this.message = "You can't do this action!";
	}
	
}
