package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.effect.EffectSet;
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
		return new CharacterCard(name, cardEra, possibleRequirement.toArray(new Requirement[0]), costs.toArray(new ImmProperties [0]), instantEffect, permanentEffects.toArray(new EffectSet[0]));
	}
}
 