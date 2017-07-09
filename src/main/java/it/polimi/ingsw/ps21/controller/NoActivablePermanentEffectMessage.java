package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class NoActivablePermanentEffectMessage extends Message {

	public NoActivablePermanentEffectMessage(PlayerColor dest, int id) {
		super(dest, id);
		this.message = "You don't have necessary Resources to activate any Permanent Effect";
	}
	
	

}
