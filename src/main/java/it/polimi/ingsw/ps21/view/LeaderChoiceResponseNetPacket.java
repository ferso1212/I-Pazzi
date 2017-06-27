package it.polimi.ingsw.ps21.view;

import java.io.Serializable;

import it.polimi.ingsw.ps21.client.NetPacket;
import it.polimi.ingsw.ps21.client.PacketType;

public class LeaderChoiceResponseNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5244025015011336879L;
	int chosen;

	public LeaderChoiceResponseNetPacket(int messNum, int chosen) {
		super(messNum);
		this.chosen = chosen;
		this.type=PacketType.LEADER_CARD_CHOICE;
	}

	public int getChosen() {
		return chosen;
	}

	

}
