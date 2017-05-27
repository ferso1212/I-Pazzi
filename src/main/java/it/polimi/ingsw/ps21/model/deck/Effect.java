package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.player.Player;

public abstract class Effect {
	protected Requirement req;
	
	public Effect(Requirement req){
		this.req = req;
	}
	
	public abstract boolean activate(Player player);
	
}
