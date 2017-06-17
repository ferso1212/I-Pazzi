package it.polimi.ingsw.ps21.model.deck;

import java.io.Serializable;
import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.effect.EffectSet;


public abstract class DevelopmentCard extends Card implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -86001113989795205L;
	protected int cardEra;
	protected EffectSet instantEffect;
	protected ArrayList<EffectSet> permanentEffects;

	
	
	public DevelopmentCard(String name, int era, RequirementAndCost reqs[], EffectSet instant, EffectSet... permanent){
		super(name, reqs);
		cardEra = era;
		instantEffect = instant;
		permanentEffects = new ArrayList<>();
		for (EffectSet e: permanent){
		permanentEffects.add(e);
		}
	}
	
	public DevelopmentCard(String name, int era,RequirementAndCost req, EffectSet instant, EffectSet... permanent){
		super(name, req);
		cardEra = era;
		instantEffect = instant;
		permanentEffects = new ArrayList<>();
		for (EffectSet e: permanent){
		permanentEffects.add(e);
		}
	
	}

	public int getEra(){
		return cardEra;
	}
	
	@Override
	public String toString(){ 
		StringBuilder temp = new StringBuilder();
		temp.append("Card:\n-Name: " + this.name + "\tPeriod: " + this.cardEra + "\tType: " + this.getCardType());
		temp.append("\n-Possible Requirement: ");
		for (RequirementAndCost r:possibleRequirement){
			temp.append("\n\t- Requirement: " + r.getRequirement().toString());
			temp.append("\n\t- Cost: " +  r.getCosts().toString());
		}	
		temp.append("\n-Instant Effect: " + instantEffect.toString() + "\n-Permanent Effects: ");
		for (EffectSet e: permanentEffects){
			temp.append("\n\t-" + e.toString() + ";");
		}
		return temp.toString();
	}
	
	public EffectSet getInstantEffect(){
		return instantEffect;
	}
	
	public EffectSet[] getPossibleEffects(){
		return  permanentEffects.toArray(new EffectSet[0]);
	}
	
	public abstract DevelopmentCardType getCardType();
	
	public abstract DevelopmentCard clone();
	
	public RequirementAndCost[] getCosts(){
		return  possibleRequirement.toArray(new RequirementAndCost[0]);
	}
	
}

