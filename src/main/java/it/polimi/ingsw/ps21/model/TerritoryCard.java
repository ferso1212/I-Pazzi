package it.polimi.ingsw.ps21.model;

import it.polimi.ingsw.ps21.model.ImmProperties;

public class TerritoryCard extends DevelopmentCard {
	private int diceReq;
	
	public TerritoryCard(String name, int era, Requirement req, InstantEffect instantEffect, TerritoryEffect permanentEffect1, int dicereq){
		super(name, era, req, instantEffect, (PermanentEffect) permanentEffect1);
		diceReq = dicereq;
		
	}
	
	public TerritoryCard(String name, int era, Requirement reqs[], InstantEffect instantEffect, TerritoryEffect permanentEffect1, TerritoryEffect permanentEffect2, int dicereq){
		super(name, era, reqs,  instantEffect, (PermanentEffect) permanentEffect1, (PermanentEffect) permanentEffect2);
		diceReq = dicereq;
	}
	
	public int getDiceRequirement(){
		return diceReq;
	}
}
