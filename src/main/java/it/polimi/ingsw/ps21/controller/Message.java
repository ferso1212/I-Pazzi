package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.view.UserHandler;
import it.polimi.ingsw.ps21.view.Visitor;

public class Message implements Visitable {
	
	protected String message;
	protected Visitor visitor;
	
	public Message(String message) {
		super();
		this.message = message;
		this.visitor = null;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public void accept(Visitor visitor) {
		this.visitor=visitor;
		
	}
	
	

}
