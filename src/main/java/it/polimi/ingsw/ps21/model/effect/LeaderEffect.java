package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class LeaderEffect extends Effect{

	protected Requirement requirement;
	public boolean activated = false;
	
	public LeaderEffect(Requirement req,ImmProperties cost) {
		super(cost);
		requirement = req;
	}

	public boolean isActivated() {
		return activated;
	}
	
	public abstract void resetActivation();
		
	

}
