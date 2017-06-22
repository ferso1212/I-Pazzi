package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

public class ActionRequestNetPacket extends NetPacket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3973929097537420399L;
	private int id;

	public ActionRequestNetPacket(int messNum, int id) {
		super(messNum);
		this.type=PacketType.ACTION;
		this.id=id;
	}

	public int getId() {
		return id;
	}
	
	
}
