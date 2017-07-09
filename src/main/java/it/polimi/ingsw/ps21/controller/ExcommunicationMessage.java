package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class ExcommunicationMessage extends Message{

	public ExcommunicationMessage(PlayerColor dest, String description, int id) {
		super(dest, id);
		this.message = "You have received the following excommunication: " + description;
	}

}
