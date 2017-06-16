package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

public class ExtraActionChoiceResponseNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2253635952209763575L;
	private int chosen;

	public ExtraActionChoiceResponseNetPacket(int messNum, int chosen) {
		super(messNum);
		this.chosen = chosen;
		this.type=PacketType.EXTRA_ACTION_CHOICE;
	}

	public int getChosen() {
		return chosen;
	}


	
	
}
