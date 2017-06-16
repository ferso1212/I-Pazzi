package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class PrivilegeChoiceResponseNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8495249469969294983L;
	ImmProperties[] chosen;
	
	public PrivilegeChoiceResponseNetPacket(int messNum) {
		super(messNum);
		this.type=PacketType.PRIVILEGES_CHOICE;
	}

	public ImmProperties[] getChosen() {
		return chosen;
	}
	
	

	
}
