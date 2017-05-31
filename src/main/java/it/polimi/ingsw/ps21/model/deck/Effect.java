package it.polimi.ingsw.ps21.model.deck;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.controller.UnchosenException;
import it.polimi.ingsw.ps21.model.player.Player;

public abstract class Effect {
	protected ArrayList<Requirement> req;
	
	
	public Effect(Requirement reqs[]){
		req = new ArrayList<>();
		for (Requirement r: reqs){
			req.add(r);
		}
	}
	
	public Effect(Requirement requirement) {
		req = new ArrayList<>();
		req.add(requirement);
	}

	public boolean isActivable(Player player){
		return true; //metodo di ripiego
		// return player.checkRequirement(req.getRequirement());
	}
	
	public abstract void activate(Player player) throws UnchosenException;
	
	public Requirement[] getReqs()
	{
		return this.req.toArray(new Requirement[0]);
	}

	
	
	
}
