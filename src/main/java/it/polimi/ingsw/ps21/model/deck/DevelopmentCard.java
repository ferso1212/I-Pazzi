package it.polimi.ingsw.ps21.model.deck;

import java.io.Serializable;
import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;


public abstract class DevelopmentCard extends Card implements Serializable{

	protected int cardEra;
	protected Effect instantEffect;
	protected ArrayList<Effect> permanentEffects;
	protected ArrayList<ImmProperties> costs;
	
	
	public DevelopmentCard(String name, int era, Requirement reqs[], ImmProperties costs[], Effect instant, Effect... permanent){
		super(name, reqs);
		this.costs = new ArrayList<>();
		for (ImmProperties c: costs){
			this.costs.add(c);
		}
		cardEra = era;
		instantEffect = instant;
		permanentEffects = new ArrayList<>();
		for (Effect e: permanent){
		permanentEffects.add(e);
		}
	}
	
	public DevelopmentCard(String name, int era, Requirement req, ImmProperties cost, Effect instant, Effect... permanent){
		super(name, req);
		this.costs = new ArrayList<>();
		this.costs.add(cost);
		cardEra = era;
		instantEffect = instant;
		permanentEffects = new ArrayList<>();
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
		for (int i=0; i< possibleRequirement.size(); i++){
			if (i!=0) temp.append(", ");
			temp.append(possibleRequirement.get(i));
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
	
	public Effect[] getPemanentEffect(){
		return (Effect []) permanentEffects.toArray(); // Metodo di ripiego, si deve implementare la scelta di effetti permanenti
	}
	public abstract DevelopmentCardType getCardType();
	
	public ImmProperties[] getCosts(){
		return (ImmProperties []) costs.toArray();
	}
	
}

