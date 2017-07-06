package it.polimi.ingsw.ps21.client;

import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;

public class DevCardChoiceRequestNetPacket extends NetPacket{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4014663409327909690L;
	private DevelopmentCard[] choices;

	public DevCardChoiceRequestNetPacket(int messNum, DevelopmentCard[] choices) {
		super(messNum);
		this.choices = choices;
		this.type=PacketType.DEV_CARD_CHOICE;
	}

	public DevelopmentCard[] getChoices() {
		return choices;
	};
	
	
}
