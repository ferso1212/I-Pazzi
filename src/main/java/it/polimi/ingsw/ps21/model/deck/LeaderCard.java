package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.effect.LeaderEffect;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class LeaderCard extends Card {
	
	private boolean activated = false;
	
	protected LeaderEffect leaderEffect;
	
	public LeaderCard(String name, Requirement reqs[], LeaderEffect effect) {
		super(name, reqs);
		this.leaderEffect = effect;	
	}
	
	public LeaderEffect getEffect(){
		return leaderEffect;
	}
	/**
	 * @return the activated
	 */
	public boolean isClonable() { // Use
		return leaderEffect.isClonable();
	}
	
	public abstract void resetActivation();
	 
	
}
