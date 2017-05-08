package it.polimi.ingsw.ps21;

import it.polimi.ingsw.ps21.model.ImmProperties;

public class BuildingCard extends DevelopmentCard{
	private int diceRequirements; 
	
	public BuildingCard(String name, int era, ImmProperties req, InstantEffect ins, PermanentEffect perm, int diceReq){
		super(name, era, req, ins, perm);
		// if (!(perm instanceOf BuildingEffect) throw new exception
		this.diceRequirements = diceReq;
	}
	
	public int getDiceRequirement(){
		return diceRequirements;
	}

}
