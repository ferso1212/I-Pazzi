package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CharacterCard extends DevelopmentCard {

	public CharacterCard(String name, int era, OrRequirement req,OrCosts cost, Effect ins,
			Effect...perms) {
		super(name, era, req, cost, ins, perms);
	}
	
	public DevelopmentCardType getCardType()
	{
		return DevelopmentCardType.CHARACTER;
	}
}
 