package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

public class RulesChoiceRequestNetPacket extends NetPacket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 308686204868897937L;

	public RulesChoiceRequestNetPacket(int messNum) {
		super(messNum);
		this.type=PacketType.RULES_CHOICE;
	}
	
	

}
