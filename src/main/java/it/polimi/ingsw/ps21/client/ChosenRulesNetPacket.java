package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

public class ChosenRulesNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6111618531545899325L;
	int chosenRules;
	
	public ChosenRulesNetPacket(int messNum, int chosenRules) {
		super(messNum);
		this.chosenRules = chosenRules;
		this.type=PacketType.RULES_CHOICE;
	}

	public int getChosenRules() {
		return chosenRules;
	}

	

}
