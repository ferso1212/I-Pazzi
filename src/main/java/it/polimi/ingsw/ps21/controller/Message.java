package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class Message {
	
	protected String message;
	protected PlayerColor dest;
	protected boolean visited = false;
	protected int actionId;

	
	public Message(PlayerColor dest, int id) {
		super();
		this.dest = dest;
		this.actionId=id;
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

	public int getActionId() {
		return actionId;
	}
	
	
	
		

}
