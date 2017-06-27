package it.polimi.ingsw.ps21.view;

import java.io.Serializable;

import it.polimi.ingsw.ps21.client.NetPacket;
import it.polimi.ingsw.ps21.client.PacketType;
import it.polimi.ingsw.ps21.model.deck.LeaderCard;

public class LeaderChoiceRequestNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7546352028206244684L;
	private LeaderCard[] choices;

	public LeaderChoiceRequestNetPacket(int messNum, LeaderCard[] choices) {
		super(messNum);
		this.choices = choices;
		this.type=PacketType.LEADER_CARD_CHOICE;
	}

	public LeaderCard[] getChoices() {
		return choices;
	}
	
	
}
