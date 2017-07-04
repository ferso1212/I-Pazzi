package it.polimi.ingsw.ps21.client;

public class ServantsNumRequestNetPacket extends NetPacket{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7062741580952658920L;
	int max;

	public ServantsNumRequestNetPacket(int messNum, int max) {
		super(messNum);
		this.max = max;
		this.type=PacketType.SERVANTS_CHOICE;
	}

	public int getMax() {
		return max;
	}
	
	
}
