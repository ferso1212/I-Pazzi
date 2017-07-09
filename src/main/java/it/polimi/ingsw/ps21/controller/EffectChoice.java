package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.effect.EffectSet;
import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class EffectChoice extends Message{
	
	private EffectSet[] possibleEffects;
	private EffectSet effectChosen;
	
	public EffectChoice(PlayerColor dest, EffectSet[] possibleEffects, int id) {
		super(dest, id);
		this.possibleEffects = possibleEffects;
	}

	public EffectSet getEffectChosen() {
		return effectChosen;
	}

	public void setEffectChosen(EffectSet effectChosen) {
		this.effectChosen = effectChosen;
	}

	public EffectSet[] getPossibleEffects() {
		return possibleEffects;
	}
	

}
