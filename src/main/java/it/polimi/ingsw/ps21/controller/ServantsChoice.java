package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class ServantsChoice extends Message {
	
	private int numberOfServants = 0;
	
	public ServantsChoice(PlayerColor player){
		super(player);
		this.message = "How many servants do you want to use?";
	}
	
	public void setNumberOfServants(int num){
		this.numberOfServants = num;
	}
	
	public int getNumberOfServants(){
		return this.numberOfServants;
	}
}
