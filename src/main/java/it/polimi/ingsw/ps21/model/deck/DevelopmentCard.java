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
	protected transient Optional<RequirementAndCost> chosenCost;
	protected transient Optional<EffectSet> chosenEffect;
	
	
	public DevelopmentCard(String name, int era, RequirementAndCost reqs[], EffectSet instant, EffectSet... permanent){
		super(name, reqs);
		if (possibleRequirement.size()==1) chosenCost = Optional.of(possibleRequirement.get(0));
		else chosenCost = Optional.empty();
		cardEra = era;
		instantEffect = instant;
		permanentEffects = new ArrayList<>();
		for (EffectSet e: permanent){
		permanentEffects.add(e);
		}
		if (permanentEffects.size() == 1) chosenEffect = Optional.of(permanentEffects.get(0));
		else chosenEffect = Optional.empty();
	}
	
	public DevelopmentCard(String name, int era,RequirementAndCost req, EffectSet instant, EffectSet... permanent){
		super(name, req);
		chosenCost = Optional.of(req);
		cardEra = era;
		instantEffect = instant;
		permanentEffects = new ArrayList<>();
		for (EffectSet e: permanent){
		permanentEffects.add(e);
		}
		if (permanentEffects.size() == 1) chosenEffect = Optional.of(permanentEffects.get(0));
		else chosenEffect = Optional.empty();
	}

	public int getEra(){
		return cardEra;
	}
	
	@Override
	public String toString(){ 
		StringBuilder temp = new StringBuilder();
		temp.append("\nPossible Requirements: ");
		for (int i=0; i< possibleRequirement.size(); i++){
			if (i!=0) temp.append(", ");
			temp.append(possibleRequirement.get(i));
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
	
	public EffectSet getChosenPemanentEffect()throws UnchosenException{
		if (chosenEffect.isPresent()) throw new UnchosenException();
		return null; //TODO Metodo di ripiego, si deve implementare la scelta di effetti permanenti
	}
	public abstract DevelopmentCardType getCardType();
	
	public abstract DevelopmentCard clone();
	
	public RequirementAndCost[] getCosts(){
		return  possibleRequirement.toArray(new RequirementAndCost[0]);
	}
	
}

