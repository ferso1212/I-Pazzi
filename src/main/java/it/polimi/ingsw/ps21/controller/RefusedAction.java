package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.player.PlayerColor;


public class RefusedAction extends Message{
	
	private String problem;

	public RefusedAction(PlayerColor dest) {
		super(dest);
		this.message = "You can't do this action!";
	}
	
	public RefusedAction(PlayerColor dest, String problem){
		super(dest);
		this.message = "You can't do this action!";
		this.problem = problem;
	}
	
	
}
