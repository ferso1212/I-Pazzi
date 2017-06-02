package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.deck.Card;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class LeaderCard extends Card {
	
	protected LeaderEffect leaderEffect;
	
	public LeaderCard(String name, Requirement reqs[], LeaderEffect effect) {
		super(name, reqs);
		this.leaderEffect = effect;	
	}
	
	public LeaderEffect getEffect(){
		return leaderEffect;
	}
	
	public abstract void activate(Player player);

	/**
	 * @return the activated
	 */
	public boolean isActivated() {
		return leaderEffect.isActivated();
	}
	
	public abstract void resetActivation();
	 
	
}
