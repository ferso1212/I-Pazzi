package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

public class StartInfoNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6111618531545899325L;
	int chosenRules;
	String name;
	
	public StartInfoNetPacket(int messNum, int chosenRules, String name) {
		super(messNum);
		this.chosenRules = chosenRules;
		this.name = name;
		this.type=PacketType.START_INFO;
	}

	public int getChosenRules() {
		return chosenRules;
	}

	public String getName() {
		return name;
	}
	
	

}
