package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

/**
 * Implementation of Venture card
 * @author daniele
 *
 */
public class VentureCard extends DevelopmentCard {

	public VentureCard(String name, int era, Requirement reqs[], ImmProperties costs[], EffectSet ins, EffectSet perm) {
		super(name, era, reqs, costs, ins, perm);
	}
	
	public VentureCard(String name, int era, Requirement req, ImmProperties cost, EffectSet ins, EffectSet perm) {
		super(name, era, req, cost, ins, perm);
	}
	
	public DevelopmentCardType getCardType()
	{
		return DevelopmentCardType.VENTURE;
	}
	
	@Override
	public DevelopmentCard clone() {
		return new VentureCard(name, cardEra, (Requirement[])possibleRequirement.toArray(),(ImmProperties []) costs.toArray(), instantEffect,permanentEffects.get(0));
	}

}
