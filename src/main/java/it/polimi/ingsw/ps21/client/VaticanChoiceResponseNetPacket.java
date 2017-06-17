package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

public class VaticanChoiceResponseNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -206833778237362215L;
	boolean supportsVatican;

	public VaticanChoiceResponseNetPacket(int messNum, boolean supportsVatican) {
		super(messNum);
		this.supportsVatican = supportsVatican;
		this.type=PacketType.VATICAN_CHOICE;
	}

	public boolean supportsVatican() {
		return supportsVatican;
	}
	
	

}
