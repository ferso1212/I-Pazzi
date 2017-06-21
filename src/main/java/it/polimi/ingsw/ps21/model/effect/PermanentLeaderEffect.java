package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class PermanentLeaderEffect extends LeaderEffect {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5775873381817340384L;
	public PermanentLeaderEffect(Requirement req){
		super(req);
	}
	
	public PermanentLeaderEffect(Requirement reqs[]){
		super(reqs);
	}
	@Override
	public void resetActivation(){		
	}

}
