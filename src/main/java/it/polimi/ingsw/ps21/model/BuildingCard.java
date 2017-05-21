package it.polimi.ingsw.ps21.model;


public class BuildingCard extends DevelopmentCard{
	private int diceRequirements; 
	
	public BuildingCard(String name, int era, Requirement req, Effect instant, Effect permanent, int diceReq){
		super(name, era, req, instant, permanent);
		this.diceRequirements = diceReq;
	}
	
	public int getDiceRequirement(){
		return diceRequirements;
	}
}
