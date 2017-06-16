package it.polimi.ingsw.ps21.client;

public class ChatMessageNetPacket extends NetPacket{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1225553963275214731L;
	private String message;

	public ChatMessageNetPacket(int messNum, String message) {
		super(messNum);
		this.message=message;
		this.type=PacketType.CHAT_MESSAGE;
	}

	public String getMessage() {
		return message;
	}

}
