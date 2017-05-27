package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

/**
 * Implementation of Territory card that can not accept requirement and is a WorkCard (it has a dice requirement)
 * @author daniele
 *
 */

public class TerritoryCard extends DevelopmentCard {
	private int diceReq;
	
	public TerritoryCard(String name, int era, Effect instantEffect, TerritoryEffect permanentEffect1, int dicereq){
		super(name, era, new OrRequirement(new Requirement(new CardsNumber(0, 0, 0, 0), new ImmProperties(0,0,0,0,0,0,0) )), new OrCosts(new ImmProperties(0,0,0,0,0,0)), (Effect) instantEffect,   (Effect) permanentEffect1);
		diceReq = dicereq;
		
	}
	
	public int getDiceRequirement(){
		return diceReq;
	}
	
	public DevelopmentCardType getCardType()
	{
		return DevelopmentCardType.TERRITORY;
	}
}
