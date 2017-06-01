package it.polimi.ingsw.ps21.model.deck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

import it.polimi.ingsw.ps21.controller.UnchosenException;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;


public abstract class DevelopmentCard extends Card{

	protected int cardEra;
	protected EffectSet instantEffect;
	protected ArrayList<EffectSet> permanentEffects;
	protected ArrayList<ImmProperties> costs;
	protected Optional<ImmProperties> chosenCost;
	protected Optional<EffectSet> chosenEffect;
	
	
	public DevelopmentCard(String name, int era, Requirement reqs[], ImmProperties costs[], EffectSet instant, EffectSet... permanent){
		super(name, reqs);
		this.costs = new ArrayList<>();
		for (ImmProperties c: costs){
			this.costs.add(c);
		}
		if (this.costs.size() == 1) chosenCost =  Optional.of(this.costs.get(0));
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
	
	public DevelopmentCard(String name, int era, Requirement req, ImmProperties cost, EffectSet instant, EffectSet... permanent){
		super(name, req);
		this.costs = new ArrayList<>();
		this.costs.add(cost);
		chosenCost = Optional.of(cost);
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
		return (EffectSet []) permanentEffects.toArray();
	}
	
	public ImmProperties[] getPossibleCosts(){
		return (ImmProperties []) costs.toArray(new ImmProperties[0]);
	}
	public EffectSet getChosenPemanentEffect()throws UnchosenException{
		if (chosenEffect.isPresent()) throw new UnchosenException();
		return null; //TODO Metodo di ripiego, si deve implementare la scelta di effetti permanenti
	}
	public abstract DevelopmentCardType getCardType();
	
	public abstract DevelopmentCard clone();
	
	public ImmProperties[] getCosts(){
		return (ImmProperties []) costs.toArray(new ImmProperties[0]);
	}
	
}

