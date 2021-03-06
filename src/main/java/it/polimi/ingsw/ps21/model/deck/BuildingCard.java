package it.polimi.ingsw.ps21.model.deck;

import it.polimi.ingsw.ps21.model.effect.EffectSet;

/**
 * Implementation of Building Card that is a WorkCard and so it has a dice requirement for WorkAction
 * @author daniele
 *
 */

public class BuildingCard extends DevelopmentCard{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5516927106001929371L;
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
	
	public String toString(){
		StringBuilder temp = new StringBuilder(super.toString());
		temp.append("\nDice Requirement: " + diceRequirement);
		return temp.toString();
	}
}
