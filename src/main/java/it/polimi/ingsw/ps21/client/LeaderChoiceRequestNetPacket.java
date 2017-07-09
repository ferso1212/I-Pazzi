package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

import it.polimi.ingsw.ps21.model.deck.LeaderCard;

public class LeaderChoiceRequestNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7546352028206244684L;
	private LeaderCard[] choices;
	private boolean isInitialChoice;

	public LeaderChoiceRequestNetPacket(int messNum, LeaderCard[] choices, boolean isInitialChoice) {
		super(messNum);
		this.choices = choices;
		this.type=PacketType.LEADER_CARD_CHOICE;
		this.isInitialChoice=isInitialChoice;
	}

	public LeaderCard[] getChoices() {
		return choices;
	}

	public boolean isInitialChoice() {
		return isInitialChoice;
	}
	
	
	
}
