package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

public class ActionRequestNetPacket extends NetPacket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3973929097537420399L;

	public ActionRequestNetPacket(int messNum) {
		super(messNum);
		this.type=PacketType.ACTION_REQUEST;
	}
	

}
