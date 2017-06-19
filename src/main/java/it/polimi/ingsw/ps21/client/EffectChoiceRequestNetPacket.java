package it.polimi.ingsw.ps21.client;


import java.io.Serializable;

import it.polimi.ingsw.ps21.model.effect.EffectSet;

public class EffectChoiceRequestNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7926501073786500703L;
	private EffectSet[] possibleEffects;

	public EffectChoiceRequestNetPacket(int messNum, EffectSet[] possibleEffects) {
		super(messNum);
		this.possibleEffects = possibleEffects;
		this.type=PacketType.EFFECT_CHOICE;
	}

	public EffectSet[] getPossibleEffects() {
		return possibleEffects;
	}
	
	

}
