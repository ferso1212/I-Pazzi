package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class ServantsChoice extends Message {
	
	private int numberOfServants = 0;
	private int maxServants;
	
	public ServantsChoice(PlayerColor player, int max){
		super(player);
		this.message = "How many servants do you want to use?";
		this.maxServants = max;
	}
	
	public void setNumberOfServants(int num){
		this.numberOfServants = num;
	}
	
	public int getNumberOfServants(){
		return this.numberOfServants;
	}
	
	public int getMaxServants(){
		return this.maxServants;
	}
}
