package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CharacterCard extends DevelopmentCard {

	public CharacterCard(String name, int era, Requirement reqs[],ImmProperties costs[], Effect ins,
			Effect...perms) {
		super(name, era, reqs, costs, ins, perms);
	}
	
	public CharacterCard(String name, int era, Requirement req,ImmProperties cost, Effect ins,
			Effect...perms) {
		super(name, era, req, cost, ins, perms);
	}
	
	@Override
	public DevelopmentCardType getCardType()
	{
		return DevelopmentCardType.CHARACTER;
	}
}
 