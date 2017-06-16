package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class PermanentLeaderEffect extends LeaderEffect {
	
	public PermanentLeaderEffect(Requirement req){
		super(req);
	}
	@Override
	public void resetActivation(){		
	}

}
