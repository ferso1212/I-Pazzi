package it.polimi.ingsw.ps21.model.deck;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class DevelopmentCard extends Card{

	protected int cardEra;
	protected Effect instantEffect;
	protected ArrayList<Effect> permanentEffects;
	
	
	public DevelopmentCard(String name, int era, OrRequirement req, OrCosts cost, Effect instant, Effect... permanent){
		super(name, req, cost);
		cardEra = era;
		instantEffect = instant;
		permanentEffects = new ArrayList<Effect>();
		for (Effect e: permanent){
		permanentEffects.add(e);
		}
	}

	public int getEra(){
		return cardEra;
	}
	
	@Override
	public String toString(){ 
		StringBuilder temp = new StringBuilder();
		temp.append("\nPossible Requirements: ");
		for (int i=0; i< requirement.getChoices().length; i++){
			if (i!=0) temp.append(", ");
			temp.append(requirement.getChoices()[i]);
		}
		temp.append("\nInstant Effect: " + instantEffect.toString() + "\nEffetti Permanenti: ");
		for (Effect e: permanentEffects){
			temp.append(e.toString() + ", ");
		}
		temp.append("\n");
		return temp.toString();
	}
	
	public Effect getInstantEffect(){
		return instantEffect;
	}

	public abstract DevelopmentCardType getCardType();
	
}

