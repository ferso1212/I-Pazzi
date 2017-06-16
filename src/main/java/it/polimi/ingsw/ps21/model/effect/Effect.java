package it.polimi.ingsw.ps21.model.effect;

import java.io.Serializable;
import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.UnchosenException;
import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class Effect implements Serializable{
	
	protected ImmProperties cost;
	
	
	public Effect(ImmProperties cost){
		this.cost= cost;
	}
	
	
	public boolean isActivable(Player player){
		return true; //metodo di ripiego
		// return player.checkRequirement(req.getRequirement());
	}
	
	public abstract ExtraAction activate(Player player);
	
	public ImmProperties getCost()
	{
		return this.cost;
	}
	
	public abstract String getType();
	public abstract String getDesc();
	
	
	
}
