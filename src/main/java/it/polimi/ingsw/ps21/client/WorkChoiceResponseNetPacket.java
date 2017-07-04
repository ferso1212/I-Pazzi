package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

public class WorkChoiceResponseNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3860454210275010801L;
	private int chosen;

	public int getChosen() {
		return chosen;
	}

	public WorkChoiceResponseNetPacket(int messNum, int chosen) {
		super(messNum);
		this.chosen = chosen;
		this.type=PacketType.WORK_CHOICE;
	}
	
	
}
