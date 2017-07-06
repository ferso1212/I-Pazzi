package it.polimi.ingsw.ps21.client;

public class ServantsNumResponseNetPacket extends NetPacket{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4819862800564689901L;
	int chosen;

	public ServantsNumResponseNetPacket(int messNum, int chosen) {
		super(messNum);
		this.chosen = chosen;
		this.type=PacketType.SERVANTS_CHOICE;
	}

	public int getChosen() {
		return chosen;
	}
	
	
}
