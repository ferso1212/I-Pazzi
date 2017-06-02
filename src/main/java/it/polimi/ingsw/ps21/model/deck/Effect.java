package it.polimi.ingsw.ps21.model.deck;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.UnchosenException;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class Effect {
	protected ImmProperties cost;
	
	
	public Effect(ImmProperties cost){
		this.cost= cost;
	}
	
	
	public boolean isActivable(Player player){
		return true; //metodo di ripiego
		// return player.checkRequirement(req.getRequirement());
	}
	
	public abstract void activate(Player player) throws UnchosenException;
	
	public ImmProperties getCost()
	{
		return this.cost;
	}
	
	public abstract String getType();
	public abstract String getDesc();
	
	
	
}
