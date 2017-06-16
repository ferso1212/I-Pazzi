package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class PrivilegesChoiceResponseNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 904852210384163309L;

	
	ImmProperties[] chosenPrivileges;


	public PrivilegesChoiceResponseNetPacket(int messNum, ImmProperties[] chosenPrivileges) {
		super(messNum);
		this.type=PacketType.PRIVILEGES_CHOICE;
		this.chosenPrivileges = chosenPrivileges;
	}


	public ImmProperties[] getChosenPrivileges() {
		return chosenPrivileges;
	}
	

}
