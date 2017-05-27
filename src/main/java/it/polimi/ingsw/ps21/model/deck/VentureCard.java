package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

/**
 * Implementation of Venture card
 * @author daniele
 *
 */
public class VentureCard extends DevelopmentCard {

	public VentureCard(String name, int era, OrRequirement req, OrCosts cost, Effect ins, Effect perm) {
		super(name, era, req, cost, ins, perm);
	}
	
	public DevelopmentCardType getCardType()
	{
		return DevelopmentCardType.VENTURE;
	}

}
