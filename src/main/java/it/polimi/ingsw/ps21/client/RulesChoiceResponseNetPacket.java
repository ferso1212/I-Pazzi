package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

public class RulesChoiceResponseNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4647402381402856582L;
	private boolean wantsAdvanced;

	public boolean wantsAdvanced() {
		return wantsAdvanced;
	}

	public RulesChoiceResponseNetPacket(int messNum, boolean wantsAdvanced) {
		super(messNum);
		this.wantsAdvanced = wantsAdvanced;
		this.type=PacketType.RULES_CHOICE;
	}
	
	

}
