package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class PermanentLeaderEffect extends Effect {

	public PermanentLeaderEffect() {
		super(new OrRequirement(new Requirement(new CardsNumber(0, 0, 0, 0), new ImmProperties(0,0,0,0,0,0,0))));
	}
	
	public void resetAction(){
	}


}
