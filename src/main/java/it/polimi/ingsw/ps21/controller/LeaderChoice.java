package it.polimi.ingsw.ps21.controller;


import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.deck.LeaderCard;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class LeaderChoice extends Message{
	
	private ArrayList<LeaderCard> choices;
	private int chosenCard;
	
	

	public LeaderChoice(ArrayList<LeaderCard> choices, PlayerColor destination, int id) {
		super(destination, id);
		this.choices = choices;
	}

	public LeaderCard getChosenCard() {
		LeaderCard chosen= choices.remove(chosenCard);
		return chosen;
	}

	public void setChosenCard(int chosenCard) {
		this.chosenCard = chosenCard;
	}

	public ArrayList<LeaderCard> getChoices() {
		return choices;
	}
	

}
