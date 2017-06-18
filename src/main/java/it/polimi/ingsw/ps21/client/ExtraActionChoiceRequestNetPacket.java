package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

import it.polimi.ingsw.ps21.view.ActionData;
import it.polimi.ingsw.ps21.view.ExtraActionData;

public class ExtraActionChoiceRequestNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2341265868277357615L;
	private ExtraActionData[] actions;

	public ExtraActionChoiceRequestNetPacket(int messNum, ExtraActionData[] actions) {
		super(messNum);
		this.actions = actions;
		this.type= PacketType.EXTRA_ACTION_CHOICE;
	}

	public ExtraActionData[] getActions() {
		return actions;
	}
	
	

}
