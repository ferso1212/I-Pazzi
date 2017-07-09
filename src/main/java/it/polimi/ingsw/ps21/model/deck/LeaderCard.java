package it.polimi.ingsw.ps21.model.deck;

import java.io.Serializable;
import it.polimi.ingsw.ps21.model.effect.LeaderEffect;

public class LeaderCard extends Card implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1178768088913198746L;
	
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
	
	public void resetActivation() {
		this.leaderEffect.resetActivation();
	};
	
	public boolean isActivated()
	{
		return this.leaderEffect.isActivated();
	}

	@Override
	public Card clone() {
		return new LeaderCard(this.name, this.leaderEffect);
	}
	 
	public Requirement[] getLeaderRequirements(){
		return leaderEffect.getRequirement();
	}
	
	public String toString(){
		StringBuilder temp = new StringBuilder();
		temp.append("Name = " + getName() +"\t Effect: " + leaderEffect.getType() + ": " + leaderEffect.getDesc());
		for (Requirement r : leaderEffect.getRequirement()) {
			temp.append("\n\tEffect Requirement: " + r.toString());
		}
		temp.append("\nIs activated: " + this.isActivated());
		return temp.toString();
	}
	
}
