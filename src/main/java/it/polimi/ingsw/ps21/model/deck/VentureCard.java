package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

/**
 * Implementation of Venture card
 * @author daniele
 *
 */
public class VentureCard extends DevelopmentCard {

	public VentureCard(String name, int era, RequirementAndCost reqs[], EffectSet ins, EffectSet perm) {
		super(name, era, reqs, ins, perm);
	}
	
	public VentureCard(String name, int era, RequirementAndCost req, EffectSet ins, EffectSet perm) {
		super(name, era, req, ins, perm);
	}
	
	public DevelopmentCardType getCardType()
	{
		return DevelopmentCardType.VENTURE;
	}
	
	@Override
	public DevelopmentCard clone() {
		return new VentureCard(name, cardEra, possibleRequirement.toArray(new RequirementAndCost[0]), instantEffect,permanentEffects.get(0));
	}

}
