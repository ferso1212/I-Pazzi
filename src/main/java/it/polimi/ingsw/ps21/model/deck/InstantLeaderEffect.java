package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class InstantLeaderEffect extends Effect{

	public InstantLeaderEffect(){
		super(new Requirement(new CardsNumber(0,0,0,0), new ImmProperties(0,0,0,0)));
		
	}
}
