package it.polimi.ingsw.ps21.model;

public class TerritoryCard extends DevelopmentCard {
	private int diceReq;
	
	public TerritoryCard(String name, int era, Requirement req, Effect instantEffect, TerritoryEffect permanentEffect1, int dicereq){
		super(name, era, req, instantEffect, (Effect) permanentEffect1);
		diceReq = dicereq;
		
	}
	
	public TerritoryCard(String name, int era, Requirement reqs[], Effect instantEffect, TerritoryEffect permanentEffect1, TerritoryEffect permanentEffects[], int dicereq){
		super(name, era, reqs,  instantEffect, (Effect[]) permanentEffects);
		diceReq = dicereq;
	}
	
	public int getDiceRequirement(){
		return diceReq;
	}
}
