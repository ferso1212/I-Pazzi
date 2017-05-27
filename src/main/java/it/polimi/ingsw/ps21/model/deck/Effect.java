package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.player.Player;

public abstract class Effect {
	protected Requirement req;
	
	public Effect(Requirement req){
		this.req = req;
	}
	
	public boolean isActivable(Player player){
		return player.checkRequirement(req);
	}
	
	public abstract void activate(Player player);
	
}
