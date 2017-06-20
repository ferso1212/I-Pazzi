package it.polimi.ingsw.ps21.model.deck;

import java.io.Serializable;
import it.polimi.ingsw.ps21.model.effect.LeaderEffect;

public class LeaderCard extends Card implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1178768088913198746L;

	private boolean activated = false;
	
	protected LeaderEffect leaderEffect;
	
	public LeaderCard(String name, LeaderEffect effect) {
		super(name);
		this.leaderEffect = effect;	
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
	
	public void resetActivation(){
		// TODO 
	};
	
	public boolean isActivated()
	{
		return this.activated;
	}

	@Override
	public Card clone() {
		// TODO Auto-generated method stub
		return null;
	}
	 
	public Requirement getRequirement(){
		return leaderEffect.getRequirement();
	}
	
}
