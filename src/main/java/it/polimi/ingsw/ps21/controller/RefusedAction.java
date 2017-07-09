package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.player.PlayerColor;


public class RefusedAction extends Message{
	
	private String problem;

	public RefusedAction(PlayerColor dest, int id) {
		super(dest, id);
		this.message = "You can't do this action!";
	}
	
	public RefusedAction(PlayerColor dest, String problem, int id){
		super(dest, id);
		this.message = "You can't do this action!";
		this.problem = problem;
	}
	
	@Override
	public String getMessage()
	{
		if (problem != null) return message + "\t" + problem;
		else return message;
	}
}
