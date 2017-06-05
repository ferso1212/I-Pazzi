package it.polimi.ingsw.ps21.controller;

import it.polimi.ingsw.ps21.model.effect.Effect;

public class EffectData {
	
	public String description;
	public EffectData(Effect effect){
		this.description = effect.getDesc();
	}
}
