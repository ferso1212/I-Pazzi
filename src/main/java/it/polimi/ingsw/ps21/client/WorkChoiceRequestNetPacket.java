package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

import it.polimi.ingsw.ps21.model.deck.DevelopmentCard;

public class WorkChoiceRequestNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5196268695638069707L;
	private DevelopmentCard card;
	private boolean cost;

	public DevelopmentCard getCard() {
		return card;
	}
	
	public boolean haveCost() {
		return cost;
	}


	public WorkChoiceRequestNetPacket(int messNum, DevelopmentCard card, boolean cost) {
		super(messNum);
		this.card = card;
		this.type=PacketType.WORK_CHOICE;
		this.cost=cost;
	}
	
	

}
