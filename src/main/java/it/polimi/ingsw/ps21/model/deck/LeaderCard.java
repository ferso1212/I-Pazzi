package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class LeaderCard extends Card {
	
	protected boolean activated = false;
	protected Effect leaderEffect;
	
	public LeaderCard(String name, Requirement reqs[], Effect effect) {
		super(name, reqs);
		this.leaderEffect = effect;	
	}
	
	public Effect getEffect(){
		return leaderEffect;
	}
	
	public abstract void activate(Player player);

	/**
	 * @return the activated
	 */
	public boolean isActivated() {
		return activated;
	}
	
	 
	
}
