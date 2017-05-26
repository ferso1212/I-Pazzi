package it.polimi.ingsw.ps21.model;

/**
 * Implementation of Territory card that can not accept requirement and is a WorkCard (it has a dice requirement)
 * @author daniele
 *
 */

public class TerritoryCard extends DevelopmentCard {
	private int diceReq;
	
	public TerritoryCard(String name, int era, ImmProperties cost, Effect instantEffect, TerritoryEffect permanentEffect1, int dicereq){
		super(name, era, new Requirement(new CardsNumber(0, 0, 0, 0), new ImmProperties(0, 0 , 0 , 0)), cost, instantEffect,   (Effect) permanentEffect1);
		diceReq = dicereq;
		
	}
	
	public TerritoryCard(String name, int era, ImmProperties cost, Effect instantEffect, TerritoryEffect permanentEffect1, TerritoryEffect permanentEffects[], int dicereq){
		super(name, era, new Requirement(new CardsNumber(0, 0, 0, 0), new ImmProperties(0, 0 , 0 , 0)), cost, instantEffect, (Effect[]) permanentEffects);
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
