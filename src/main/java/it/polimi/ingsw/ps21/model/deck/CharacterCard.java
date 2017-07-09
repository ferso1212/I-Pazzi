package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.effect.EffectSet;

public class CharacterCard extends DevelopmentCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1775216900129099992L;

	public CharacterCard(String name, int era, RequirementAndCost reqs[], EffectSet ins,
			EffectSet...perms) {
		super(name, era, reqs, ins, perms);
	}
	
	public CharacterCard(String name, int era, RequirementAndCost req, EffectSet ins,
			EffectSet...perms) {
		super(name, era, req, ins, perms);
	}
	
	@Override
	public DevelopmentCardType getCardType()
	{
		return DevelopmentCardType.CHARACTER;
	}

	@Override
	public DevelopmentCard clone() {
		return new CharacterCard(name, cardEra, possibleRequirement.toArray(new RequirementAndCost[0]), instantEffect, permanentEffects.toArray(new EffectSet[0]));
	}
}
 