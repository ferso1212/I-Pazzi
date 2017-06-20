package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

public class InitNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1168251197284735091L;
	private boolean newMatch;

	public InitNetPacket(int messNum, boolean newMatch) {
		super(messNum);
		this.newMatch = newMatch;
		this.type=PacketType.INIT;
	}

	public boolean isNew() {
		return newMatch;
	}
	
	

}
