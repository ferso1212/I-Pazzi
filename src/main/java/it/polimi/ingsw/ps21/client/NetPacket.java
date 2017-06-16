package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

public abstract class NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 73696031937536316L;
	protected PacketType type;
	//private Object o;
	//private Object[] additionalObjects;
	protected int num;
	
	/**
	 * Builds a network package that carries a single object.
	 * @param type the type of the packet
	 * @param o	the object to send
	 * @param messNum a number that identifies the communication between server and client
	 */
	public NetPacket(int messNum) {
		super();
		this.num = messNum;
	
	}
	

	/**
	 * Returns the packet type.
	 * @return a value of PacketType enum representing the type of the packet
	 */
	public PacketType getType() {
		return type;
	}


	public int getNum() {
		return num;
	}
	
}
