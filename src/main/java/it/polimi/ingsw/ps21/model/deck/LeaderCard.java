package it.polimi.ingsw.ps21.model.deck;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.effect.LeaderEffect;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class LeaderCard extends Card {
	
	private boolean activated = false;
	
	protected LeaderEffect leaderEffect;
	protected Requirement requirement;
	
	public LeaderCard(String name, LeaderEffect effect) {
		super(name);
		this.leaderEffect = effect;	
		this.requirement = new Requirement(new CardsNumber(0,0,0,0), new ImmProperties(0));
	}
	
	public LeaderEffect getEffect(){
		return leaderEffect;
	}
	/**
	 * @return the activated
	 */
	public boolean isClonable() { // Method to implements Lorenzo il Magnifico execution
		return leaderEffect.isClonable();
	}
	
	public abstract void resetActivation();
	
	public boolean isActivated()
	{
		return this.activated;
	}
	 
	
}
