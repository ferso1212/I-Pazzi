package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class LeaderCard extends Card {
	
	protected boolean activated = false;
	protected Effect leaderEffect;
	
	public LeaderCard(String name, OrRequirement req, Effect effect) {
		super(name, req, new OrCosts(new ImmProperties(0,0,0,0,0,0,0)));
		this.leaderEffect = effect;	
	}
	
	public Effect getEffect(){
		return leaderEffect;
	}
	
	public abstract void activate(Player player);
	public abstract void resetAction();
	
	 
	
}
