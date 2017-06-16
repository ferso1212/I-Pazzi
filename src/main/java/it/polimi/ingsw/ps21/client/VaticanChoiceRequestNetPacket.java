package it.polimi.ingsw.ps21.client;

public class VaticanChoiceRequestNetPacket extends NetPacket{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5998445824533903368L;

	public VaticanChoiceRequestNetPacket(int messNum) {
		super(messNum);
		this.type=PacketType.VATICAN_CHOICE;
	}
	
}
