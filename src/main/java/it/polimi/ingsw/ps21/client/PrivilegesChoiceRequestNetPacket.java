package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class PrivilegesChoiceRequestNetPacket extends NetPacket implements Serializable{
	int privileges;

	public PrivilegesChoiceRequestNetPacket(int messNum, int acquiredPrivileges) {
		super(messNum);
		this.privileges = acquiredPrivileges;
		this.type=PacketType.PRIVILEGES_CHOICE_RESPONSE;
	}

	public int getNumberOfAcquiredPrivileges() {
		return this.privileges;
	}
	

}
