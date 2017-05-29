package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.player.Player;

public abstract class Effect {
	protected OrRequirement req;
	
	public Effect(OrRequirement req){
		this.req = req;
	}
	
	public boolean isActivable(Player player){
		return true; //metodo di ripiego
		// return player.checkRequirement(req.getRequirement());
	}
	
	public abstract void activate(Player player);
	
}
