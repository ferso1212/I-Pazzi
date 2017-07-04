package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class PickAnotherCardMessage extends Message{
	
	private DevelopmentCard[] possibleChoices;
	private DevelopmentCard cardChosen;

	public PickAnotherCardMessage(PlayerColor dest, DevelopmentCard[] possibleChoices) {
		super(dest);
		this.possibleChoices = possibleChoices;
	}

	public DevelopmentCard getCardChosen() {
		return cardChosen;
	}

	public void setCardChosen(int choice) {
		this.cardChosen = possibleChoices[choice];
	}

	public DevelopmentCard[] getPossibleChoices() {
		return possibleChoices;
	}
	
}
