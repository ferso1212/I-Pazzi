package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

import it.polimi.ingsw.ps21.view.ActionData;

public class ActionResponseNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7504345457363680453L;
	private ActionData action;

	public ActionResponseNetPacket(int messNum, ActionData data) {
		super(messNum);
		this.action = data;
		this.type=PacketType.ACTION;
	}

	public ActionData getAction() {
		return action;
	}
	
	
	
}
