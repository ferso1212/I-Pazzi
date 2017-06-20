package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class ExcommunicationMessage extends Message{

	public ExcommunicationMessage(PlayerColor dest) {
		super(dest);
		this.message = "You have received an Excommunication!";
	}

}
