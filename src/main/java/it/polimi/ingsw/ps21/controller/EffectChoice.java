package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.effect.EffectSet;

public class EffectChoice extends Message{
	
	private EffectSet[] choices;
	private EffectSet chosen;
	
	public EffectChoice(EffectSet[] choices) {
		this.message = "You have to choose an Effect.";
 		this.choices = choices;
	}

	public EffectSet getChosen() {
		return chosen;
	}

	public void setChosen(int choose) {
		this.chosen = this.choices[choose];
	}

	public EffectSet[] getChoices() {
		return choices;
	}
	
	

}
