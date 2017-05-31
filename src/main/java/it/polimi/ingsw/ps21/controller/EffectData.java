package it.polimi.ingsw.ps21.controller;

import java.io.Serializable;

import it.polimi.ingsw.ps21.model.deck.Effect;
import it.polimi.ingsw.ps21.model.deck.Requirement;

public class EffectData implements Serializable {
	private Requirement[] reqs;
	private String effectType;
	private String description;
	
	public EffectData(Effect ef)
	{
		this.reqs=ef.getReqs().clone();
		
		
	}
	
	
}
