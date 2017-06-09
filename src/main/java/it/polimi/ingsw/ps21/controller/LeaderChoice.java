package it.polimi.ingsw.ps21.controller;


import it.polimi.ingsw.ps21.model.deck.LeaderCard;

public class LeaderChoice extends Message{
	
	private LeaderCard[] choices;
	private LeaderCard choosenCard;
	
	public LeaderChoice(LeaderCard[] choices) {
		super();
		this.choices = choices;
	}

	public LeaderCard getChoosenCard() {
		return choosenCard;
	}

	public void setChoosenCard(LeaderCard choosenCard) {
		this.choosenCard = choosenCard;
	}

	public LeaderCard[] getChoices() {
		return choices;
	}
	

}
