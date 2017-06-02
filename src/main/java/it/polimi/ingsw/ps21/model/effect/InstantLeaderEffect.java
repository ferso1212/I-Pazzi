package it.polimi.ingsw.ps21.model.effect;

import it.polimi.ingsw.ps21.model.deck.CardsNumber;
import it.polimi.ingsw.ps21.model.deck.Requirement;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class InstantLeaderEffect extends LeaderEffect{

	public InstantLeaderEffect(Requirement req, ImmProperties cost){
		super(req, cost);		
	}

	@Override
	public void resetActivation(){
		activated = false;
	}
	
}
