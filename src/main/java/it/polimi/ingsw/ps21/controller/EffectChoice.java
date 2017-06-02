package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.deck.Effect;

public class EffectChoice extends Message{
	
	private Effect[] choices;
	private Effect chosen;
	
	public EffectChoice(String message, Effect[] choices) {
		super(message);
		this.choices = choices;
	}

	public Effect getChosen() {
		return chosen;
	}

	public void setChosen(int choose) {
		this.chosen = this.choices[choose];
	}

	public Effect[] getChoices() {
		return choices;
	}
	
	

}
