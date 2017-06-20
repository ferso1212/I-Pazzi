package it.polimi.ingsw.ps21.view;

import java.io.Serializable;

import it.polimi.ingsw.ps21.client.NetPacket;
import it.polimi.ingsw.ps21.client.PacketType;

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
