package it.polimi.ingsw.ps21.client;

public class DevCardChoiceResponseNetPacket extends NetPacket{
	int chosen;

	public DevCardChoiceResponseNetPacket(int messNum, int chosen) {
		super(messNum);
		this.chosen = chosen;
		this.type=PacketType.DEV_CARD_CHOICE;
	}

	public int getChosen() {
		return chosen;
	}
	
	
}
