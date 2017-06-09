package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class Message {
	
	protected String message;
	protected PlayerColor dest;
	protected boolean visited = false;

	
	public Message(PlayerColor dest) {
		super();
		this.dest = dest;
	}

	public String getMessage() {
		return message;
	}

	public PlayerColor getDest() {
		return dest;
	}

	public void setVisited() {
		this.visited = true;
	}

	public boolean isVisited() {
		return visited;
	}
	
		

}
