package it.polimi.ingsw.ps21.model.effect;

import java.io.Serializable;

import it.polimi.ingsw.ps21.model.actions.ExtraAction;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.player.AdvancedPlayer;


public abstract class LeaderEffect implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4418363160082201407L;
	protected Requirement[] requirements;
	public boolean activated = false;
	public boolean clonable =false;
	
	public LeaderEffect(Requirement reqs[]) {
		requirements = reqs;
	}

	public LeaderEffect(Requirement req){
		requirements = new Requirement[1];
		requirements[0]=req;
	}
	public boolean isActivated() {
		return activated;
	}
	
	public abstract ExtraAction activate(AdvancedPlayer player);
	
	public abstract void resetActivation();

	public boolean isClonable() {
		return clonable;
	}
	
	public abstract String getType();
	public abstract String getDesc();
	
	public Requirement[] getRequirement(){
		return this.requirements;
	}

		
	

}
