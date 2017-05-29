package it.polimi.ingsw.ps21.model.deck;


public class CharacterCard extends DevelopmentCard {

	public CharacterCard(String name, int era, OrRequirement req,OrCosts cost, Effect ins,
			Effect...perms) {
		super(name, era, req, cost, ins, perms);
	}
	
	@Override
	public DevelopmentCardType getCardType()
	{
		return DevelopmentCardType.CHARACTER;
	}
}
 