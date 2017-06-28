package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

public class RulesNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2012795322141128470L;
	private boolean advanced;

	public RulesNetPacket(int messNum, boolean advanced) {
		super(messNum);
		this.advanced = advanced;
		this.type=PacketType.RULES;
	}

	public boolean isAdvanced() {
		return advanced;
	}
	
	

}
