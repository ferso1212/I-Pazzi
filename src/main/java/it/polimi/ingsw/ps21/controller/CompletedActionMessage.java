package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class CompletedActionMessage extends Message{

	public CompletedActionMessage(PlayerColor dest, int id) {
		super(dest, id);
		this.message="You have completed your action.";
	}
	

}
