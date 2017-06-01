package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CharacterCard extends DevelopmentCard {

	public CharacterCard(String name, int era, Requirement reqs[],ImmProperties costs[], EffectSet ins,
			EffectSet...perms) {
		super(name, era, reqs, costs, ins, perms);
	}
	
	public CharacterCard(String name, int era, Requirement req,ImmProperties cost, EffectSet ins,
			EffectSet...perms) {
		super(name, era, req, cost, ins, perms);
	}
	
	@Override
	public DevelopmentCardType getCardType()
	{
		return DevelopmentCardType.CHARACTER;
	}

	@Override
	public DevelopmentCard clone() {
		return new CharacterCard(name, cardEra, (Requirement[])possibleRequirement.toArray(),(ImmProperties []) costs.toArray(), instantEffect,(EffectSet[]) permanentEffects.toArray());
	}
}
 