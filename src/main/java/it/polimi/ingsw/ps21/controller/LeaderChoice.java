package it.polimi.ingsw.ps21.controller;


import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class LeaderChoice extends Message{
	
	private LeaderCard[] choices;
	private int chosenCard;
	

	public LeaderChoice(LeaderCard[] choices, PlayerColor destination) {
		super(destination);
		this.choices = choices;
	}

	public int getChosenCard() {
		return chosenCard;
	}

	public void setChosenCard(int chosenCard) {
		this.chosenCard = chosenCard;
	}

	public LeaderCard[] getChoices() {
		return choices;
	}
	

}
