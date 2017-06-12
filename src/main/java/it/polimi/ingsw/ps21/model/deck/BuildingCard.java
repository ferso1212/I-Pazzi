package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

/**
 * Implementation of Building Card that is a WorkCard and so it has a dice requirement for WorkAction
 * @author daniele
 *
 */

public class BuildingCard extends DevelopmentCard{
	private int diceRequirement; 
	
	public BuildingCard(String name, int era, RequirementAndCost[] reqs, int diceReq, EffectSet instant, EffectSet... permanent){
		super(name, era, reqs, instant, permanent);
		this.diceRequirement = diceReq;
	}
	
	public BuildingCard(String name, int era, RequirementAndCost req, int diceReq, EffectSet instant, EffectSet... permanent){
		super(name, era, req, instant, permanent);
		this.diceRequirement = diceReq;
	}
	
	public int getDiceRequirement(){
		return diceRequirement;
	}
	
	@Override
	public DevelopmentCardType getCardType()
	{
		return DevelopmentCardType.BUILDING;
	}
	
	@Override
	public DevelopmentCard clone() {
		return new BuildingCard(name, cardEra, possibleRequirement.toArray(new RequirementAndCost[0]), diceRequirement, instantEffect, permanentEffects.toArray(new EffectSet[0]));
	}
}
