package it.polimi.ingsw.ps21.controller;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;

public class WorkMessage extends Message {

	private DevelopmentCard[] choices;
	private int[] chosenCardsAndEffects;
	private PlayerProperties clonedPlayerProperties;


	public WorkMessage(PlayerColor destination, DevelopmentCard[] choices) {
		super(destination);
		// TODO settare messaggio
		this.choices = choices;
	}

	public int[] getChosenCardsAndEffects() {
		return chosenCardsAndEffects;
	}

	public boolean setChosenCardsAndEffects(int[] playerChoices) {
		PlayerProperties totalCosts = new PlayerProperties(0);
		for (int i = 0; i < playerChoices.length; i++) {
			if (chosenCardsAndEffects[i] != 0) {
				totalCosts.increaseProperties(choices.get(i).getPossibleEffects()[playerChoices[i]].getTotalCost());
			}
		}
		if (this.clonedPlayerProperties.greaterOrEqual(totalCosts)) {
			this.chosenCardsAndEffects = playerChoices;
			return true;
		}
		return false;
	}

	public ArrayList<DevelopmentCard> getChoices() {
		return choices;
	}

}
