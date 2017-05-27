package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.DevelopmentCardType;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

/**
 * Implementation of Building Card that is a WorkCard and so it has a dice requirement for WorkAction
 * @author daniele
 *
 */

public class BuildingCard extends DevelopmentCard{
	private int diceRequirements; 
	
	public BuildingCard(String name, int era, Requirement req, ImmProperties cost, Effect instant, Effect permanent, int diceReq){
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
