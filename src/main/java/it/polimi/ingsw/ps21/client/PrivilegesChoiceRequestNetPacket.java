package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class PrivilegesChoiceRequestNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4518594077347950872L;
	private int privileges;
	private ImmProperties[] choices;

	public PrivilegesChoiceRequestNetPacket(int messNum, int acquiredPrivileges, ImmProperties[] choices) {
		super(messNum);
		this.privileges = acquiredPrivileges;
		this.type=PacketType.PRIVILEGES_CHOICE;
		this.choices=choices;
	}

	public int getNumberOfAcquiredPrivileges() {
		return this.privileges;
	}

	public ImmProperties[] getChoices() {
		return choices;
	}
	
	
	

}
