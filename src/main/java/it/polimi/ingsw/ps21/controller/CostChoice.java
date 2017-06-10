package it.polimi.ingsw.ps21.controller;

import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.player.PlayerColor;
import it.polimi.ingsw.ps21.model.player.PlayerProperties;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CostChoice extends Message {
	
	private ArrayList<ImmProperties> choices;
	private ImmProperties chosen;
	private PlayerProperties clonedPlayerProperties;

	public CostChoice(PlayerColor dest, ArrayList<ImmProperties> choices, PlayerProperties clonedPlayerProperties) {
		super(dest);
		this.choices = choices;
		this.clonedPlayerProperties = clonedPlayerProperties;
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
