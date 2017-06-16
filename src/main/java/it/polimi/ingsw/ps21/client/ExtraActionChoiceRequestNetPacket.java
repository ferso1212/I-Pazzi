package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

import it.polimi.ingsw.ps21.view.ActionData;

public class ExtraActionChoiceRequestNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2341265868277357615L;
	private ActionData[] actions;

	public ExtraActionChoiceRequestNetPacket(int messNum, ActionData[] actions) {
		super(messNum);
		this.actions = actions;
		this.type= PacketType.EXTRA_ACTION_CHOICE_REQUEST;
	}

	public ActionData[] getActions() {
		return actions;
	}
	
	

}
