package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class WorkMessage extends Message {

	private DevelopmentCard[] choices;
	private int[] chosenCardsAndEffects;
	private DevelopmentCard[] cardsToActivateWithoutChoice;


	public WorkMessage(PlayerColor destination, DevelopmentCard[] choices, DevelopmentCard[] cardsToActivateWithoutChoice, int id) {
		super(destination, id);
		this.message="You have to choose which cards to to activate.";
		this.choices = choices;
		this.cardsToActivateWithoutChoice = cardsToActivateWithoutChoice;
	}

	public int[] getChosenCardsAndEffects() {
		return chosenCardsAndEffects;
	}
	
	public DevelopmentCard[] getcardsToActivateWithoutChoice() {
		return cardsToActivateWithoutChoice;
	}

	public void setChosenCardsAndEffects(int[] playerChoices) {
			this.chosenCardsAndEffects = playerChoices;
	}

	public DevelopmentCard[] getChoices() {
		return choices;
	}

}
