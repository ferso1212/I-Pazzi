package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

/**
 * Implementation of Building Card that is a WorkCard and so it has a dice requirement for WorkAction
 * @author daniele
 *
 */

public class BuildingCard extends DevelopmentCard{
	private int diceRequirements; 
	
	public BuildingCard(String name, int era, OrRequirement req, OrCosts cost, int diceReq, Effect instant, Effect... permanent){
		super(name, era, req, cost, instant, permanent);
		this.diceRequirements = diceReq;
	}
	
	public int getDiceRequirement(){
		return diceRequirements;
	}
	
	public DevelopmentCardType getCardType()
	{
		return DevelopmentCardType.BUILDING;
	}
}
