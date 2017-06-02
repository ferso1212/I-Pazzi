package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CostChoice extends Message {
	
	private ImmProperties[] choices;
	private ImmProperties chosen;
	
	public CostChoice(String message, ImmProperties[] choices) {
		super(message);
		this.choices = choices;
	}

	public ImmProperties getChosen() {
		return chosen;
	}

	public void setChosen(int choice) {
		this.chosen = this.choices[choice];
	}

	public ImmProperties[] getChoices() {
		return choices;
	}
	
	

}
