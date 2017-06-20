package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

public class NameResponseNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1475523528738248521L;
	String name;
	
	

	public NameResponseNetPacket(int messNum, String name) {
		super(messNum);
		this.name = name;
		this.type=PacketType.NAME;
	}



	public String getName() {
		return name;
	}
	
	

}
