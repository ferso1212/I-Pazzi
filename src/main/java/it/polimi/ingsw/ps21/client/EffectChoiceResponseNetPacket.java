package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

public class EffectChoiceResponseNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7687570112792161816L;
	private int chosen;

	public EffectChoiceResponseNetPacket(int messNum, int chosen) {
		super(messNum);
		this.chosen = chosen;
		this.type=PacketType.EFFECT_CHOICE;
	}

	public int getChosen() {
		return chosen;
	}
	
	
	
}
