package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;

public class WorkMessage extends Message {

	private DevelopmentCard[] choices;
	private int[] chosenCardsAndEffects;
	private DevelopmentCard[] cardsToActivateWithoutChoice;
	private PlayerProperties clonedPlayerProperties;


	public WorkMessage(PlayerColor destination, DevelopmentCard[] choices, DevelopmentCard[] cardsToActivateWithoutChoice) {
		super(destination);
		this.message="You have to choose cards to activate. You have to put 0 if you don't want to activate the card and 1 or 2 or ... to select a specific effect to activate.";
		this.choices = choices;
		this.cardsToActivateWithoutChoice = cardsToActivateWithoutChoice;
	}

	public int[] getChosenCardsAndEffects() {
		return chosenCardsAndEffects;
	}
	
	public DevelopmentCard[] getcardsToActivateWithoutChoice() {
		return cardsToActivateWithoutChoice;
	}

	public boolean setChosenCardsAndEffects(int[] playerChoices) {
		PlayerProperties totalCosts = new PlayerProperties(0);
		for (int i = 0; i < playerChoices.length; i++) {
			if (chosenCardsAndEffects[i] != 0) {
				totalCosts.increaseProperties(choices[i].getPossibleEffects()[playerChoices[i]].getTotalCost());
			}
		}
		if (this.clonedPlayerProperties.greaterOrEqual(totalCosts)) {
			this.chosenCardsAndEffects = playerChoices;
			return true;
		}
		return false;
	}

	public DevelopmentCard[] getChoices() {
		return choices;
	}

}
