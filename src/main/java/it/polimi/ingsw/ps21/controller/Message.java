package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.view.Visitor;

public class Message implements Visitable {
	
	protected String message;
	protected Visitor visitor;

	public String getMessage() {
		return message;
	}

	@Override
	public void accept(Visitor visitor) {
		this.visitor=visitor;
		
	}
	
	

}
