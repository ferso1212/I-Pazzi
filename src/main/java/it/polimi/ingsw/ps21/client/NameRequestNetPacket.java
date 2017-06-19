package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

public class NameRequestNetPacket extends NetPacket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1332218798784697194L;

	public NameRequestNetPacket(int messNum) {
		super(messNum);
		this.type=PacketType.NAME;
	}

}
