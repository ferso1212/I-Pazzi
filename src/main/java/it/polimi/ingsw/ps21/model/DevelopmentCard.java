package it.polimi.ingsw.ps21.model;

import java.util.ArrayList;

public abstract class DevelopmentCard extends Card{

	protected int cardEra;
	protected Effect instantEffect;
	protected ArrayList<Effect> permanentEffects = new ArrayList<Effect>();
	
	
	public DevelopmentCard(String name, int era, Requirement req, ImmProperties cost, Effect instant, Effect permanent){
		super(name, req, cost);
		cardEra = era;
		instantEffect = instant;
		permanentEffects.add(permanent);
	}
	
	public DevelopmentCard(String name, int era, Requirement reqs[], ImmProperties cost, Effect instant, Effect permanent){
		super(name, reqs,cost);
		cardEra = era;
		instantEffect = instant;
		permanentEffects.add(permanent);
		
	}
	
	public DevelopmentCard(String name, int era, Requirement[] reqs,ImmProperties cost, Effect instant, Effect permanents[]){
		// TODO Auto-generated constructor stub
		super(name, reqs, cost);
		cardEra = era;
		instantEffect = instant;
		for (Effect e: permanents){
			permanentEffects.add(e);
		}
	}

	public DevelopmentCard(String name, int era, Requirement req, ImmProperties cost, Effect instant, Effect permanents[]) {
		super(name, req, cost);
		cardEra = era; 
		instantEffect = instant; 
		for (Effect e: permanents){
			permanentEffects.add(e);
		}
	}

	public int getEra(){
		return cardEra;
	}
	
	@Override
	public String toString(){ 
		StringBuilder temp = new StringBuilder();
		temp.append("Nome Carta: " + name + "; Era: " + cardEra + "\nRequisiti carta: ");
		for (Requirement r: requires){
			temp.append(r.toString());
		}
		temp.append("\nEffetto immediato: " + instantEffect.toString() + "\nEffetti Permanenti: ");
		for (Effect e: permanentEffects){
			temp.append(e.toString() + ", ");
		}
		temp.append("\n");
		return temp.toString();
	}
	
	public Effect getInstantEffect(){
		return instantEffect;
	}
	
	

	@Override
	public Requirement getRequirement() throws Exception {
		if (chosenReq == null) throw new Exception();
		return chosenReq;
	}

	public abstract DevelopmentCardType getCardType();
	
}

