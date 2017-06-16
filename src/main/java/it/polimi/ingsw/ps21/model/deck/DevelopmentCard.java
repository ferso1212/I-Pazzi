package it.polimi.ingsw.ps21.model.deck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

import it.polimi.ingsw.ps21.controller.UnchosenException;
import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;


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
		temp.append("\nPossible Requirements and costs: ");
		for (int i=0; i< possibleRequirement.size(); i++){
			if (i!=0) temp.append("\n------------------\n ");
			temp.append(possibleRequirement.get(i).toString());
		}
		temp.append("\nInstant Effect: " + instantEffect.toString() + "\nEffetti Permanenti: ");
		for (EffectSet e: permanentEffects){
			temp.append(e.toString() + ", ");
		}
		temp.append("\n");
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

