package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

public class CostChoiceResponseNetPacket extends NetPacket implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 5616211861637812926L;
int chosen;

public CostChoiceResponseNetPacket(int messNum, int chosen) {
	super(messNum);
	this.chosen = chosen;
	this.type=PacketType.COST_CHOICE;
}

public int getChosen() {
	return chosen;
}

}
