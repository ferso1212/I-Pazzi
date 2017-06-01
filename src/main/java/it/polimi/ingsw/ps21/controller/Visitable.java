package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.view.Visitor;

public interface Visitable {
	
	public abstract void accept(Visitor visitor);

}
