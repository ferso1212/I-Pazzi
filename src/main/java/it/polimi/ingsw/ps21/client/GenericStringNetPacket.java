package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

public class GenericStringNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2439540346286917701L;
	String str;

	public GenericStringNetPacket(int messNum, String str) {
		super(messNum);
		this.str = str;
		this.type=PacketType.GENERIC_STRING;
	}

	public String getStr() {
		return str;
	}
	

}
