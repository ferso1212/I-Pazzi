package it.polimi.ingsw.ps21.controller;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CostChoice extends Message {
	
	private ArrayList<ImmProperties> choices;
	private ImmProperties chosen;
	
	public CostChoice(PlayerColor destination, ArrayList<ImmProperties> choices) {
		super(destination);
		this.message = "You have to choose a cost to pay.";
		this.choices = choices;
	}

	public ImmProperties getChosen() {
		return chosen;
	}

	public void setChosen(int choice) {
		this.chosen = this.choices.get(choice);
	}

	public ArrayList<ImmProperties> getChoices() {
		return choices;
	}
	
	

}
