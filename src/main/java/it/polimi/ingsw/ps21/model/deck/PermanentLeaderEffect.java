package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class PermanentLeaderEffect extends Effect {
	
	public PermanentLeaderEffect() {
		super(new Requirement(new CardsNumber(0,0,0,0), new ImmProperties()));
	}


}
