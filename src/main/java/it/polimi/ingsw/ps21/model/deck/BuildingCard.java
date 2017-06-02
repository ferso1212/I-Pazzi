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
	
	public BuildingCard(String name, int era, Requirement reqs [], ImmProperties costs[], int diceReq, EffectSet instant, EffectSet... permanent){
		super(name, era, reqs, costs, instant, permanent);
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
		return new BuildingCard(name, cardEra, (Requirement[])possibleRequirement.toArray(),(ImmProperties []) costs.toArray(), diceRequirement, instantEffect,(EffectSet[]) permanentEffects.toArray());
	}
}
