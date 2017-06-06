package it.polimi.ingsw.ps21.controller;

public class RefusedAction extends Message{

	private String problem;
	
	public RefusedAction() {
		this.message = "You can't do this action!";
	}
	
	public RefusedAction(String problem){
		this.message = "You can't do this action!";
		this.problem = problem;
	}
	
}
