package it.polimi.ingsw.ps21.model;

import it.polimi.ingsw.ps21.model.ImmProperties;

public class TerritoryCard extends DevelopmentCard {
	private int diceReq;
	
	public TerritoryCard(String name, int era, ImmProperties req1, InstantEffect instantEffect, TerritoryEffect permanentEffect1, int dicereq){
		super(name, era, req1, instantEffect, (PermanentEffect) permanentEffect1);
		diceReq = dicereq;
		
	}
	
	public TerritoryCard(String name, int era, ImmProperties req1, ImmProperties req2, InstantEffect instantEffect, TerritoryEffect permanentEffect1, TerritoryEffect permanentEffect2, int dicereq){
		super(name, era, req1, req2, instantEffect, (PermanentEffect) permanentEffect1, (PermanentEffect) permanentEffect2);
		diceReq = dicereq;
	}
}
