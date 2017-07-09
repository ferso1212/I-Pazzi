package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class WorkMessage extends Message {

	private DevelopmentCard[] cardsWithCost;
	private int[] chosenCardsWithCost;
	private int[] chosenCardsWithoutCost;
	private DevelopmentCard[] cardsToActivateWithoutChoice;


	public WorkMessage(PlayerColor destination, DevelopmentCard[] cardsWithCosts, DevelopmentCard[] cardsToActivateWithoutChoice, int id) {
		super(destination, id);
		this.message="You have to choose which cards to to activate.";
		this.cardsWithCost = cardsWithCosts;
		this.cardsToActivateWithoutChoice = cardsToActivateWithoutChoice;
	}

	public int[] getChosenCardsWithCost() {
		return chosenCardsWithCost;
	}
	
	
	
	public int[] getChosenCardsWithoutCost() {
		return chosenCardsWithoutCost;
	}

	public void setChosenCardsWithoutCost(int[] chosenCardsWithoutCost) {
		this.chosenCardsWithoutCost = chosenCardsWithoutCost;
	}

	public void setChosenCardsWithCost(int[] chosenCardsWithCost) {
		this.chosenCardsWithCost = chosenCardsWithCost;
	}

	public DevelopmentCard[] getcardsToActivateWithoutChoice() {
		return cardsToActivateWithoutChoice;
	}


	public DevelopmentCard[] getCardsWithCost() {
		return this.cardsWithCost;
	}

	public void setCardsWithCost(DevelopmentCard[] cardsWithCost) {
		this.cardsWithCost = cardsWithCost;
	}

	public void setCardsToActivateWithoutChoice(DevelopmentCard[] cardsToActivateWithoutChoice) {
		this.cardsToActivateWithoutChoice = cardsToActivateWithoutChoice;
	}

	
}
