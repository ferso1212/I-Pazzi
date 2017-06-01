package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

/**
 * Implementation of Territory card that can not accept requirement and is a WorkCard (it has a dice requirement)
 * @author daniele
 *
 */

public class TerritoryCard extends DevelopmentCard {
	private int diceReq;
	
	public TerritoryCard(String name, int era,int dicereq, EffectSet instantEffect, EffectSet... perms ){
		super(name, era, new Requirement(new CardsNumber(0, 0, 0, 0), new ImmProperties(0,0,0,0,0,0,0)), new ImmProperties(0,0,0,0,0,0), instantEffect,   (EffectSet[]) perms);
		diceReq = dicereq;
	}
	
	public int getDiceRequirement(){
		return diceReq;
	}
	
	@Override
	public DevelopmentCardType getCardType()
	{
		return DevelopmentCardType.TERRITORY;
	}
	
	@Override
	public DevelopmentCard clone() {
		return new TerritoryCard(name, cardEra, diceReq, instantEffect,(EffectSet[]) permanentEffects.toArray());
	}
}
