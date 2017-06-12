package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

public class NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 73696031937536316L;
	private PacketType type;
	private Object o;
	private Object[] additionalObjects;
	int num;
	
	/**
	 * Builds a network package that carries a single object.
	 * @param type the type of the packet
	 * @param o	the object to send
	 * @param messNum a number that identifies the communication between server and client
	 */
	public NetPacket(PacketType type, Object o, int messNum) {
		super();
		this.type = type;
		this.o = o;
		this.num = messNum;
		this.additionalObjects=null;
	}
	
	/**
	 * Builds a network package that carries multiple objects.
	 * @param type type the type of the packet
	 * @param o the first object to send
	 * @param messNum a number that identifies the communication between server and client
	 * @param additionalObjs an array containing the other objects to send
	 */
	public NetPacket(PacketType type, Object o, int messNum, Object...additionalObjs) {
		super();
		this.type = type;
		this.o = o;
		this.num = messNum;
		int i=0;
		this.additionalObjects=new Object[additionalObjs.length];
		for(Object additionalObj: additionalObjs)
		{
			this.additionalObjects[i]=additionalObj;
			i++;
		}
	}

	/**
	 * Returns the packet type.
	 * @return a value of PacketType enum representing the type of the packet
	 */
	public PacketType getType() {
		return type;
	}

	/**
	 * Returns the main object in the packet
	 * @return
	 */
	public Object getObject() {
		return o;
	}


	public int getNum() {
		return num;
	}
	
	/**
	 * Returns the additional object in the specified position.
	 * @param index the position of the de
	 * @return
	 */
	public Object getAdditionalObject(int index)
	{
		return this.additionalObjects[index];
	}
	
	public Object[] getAdditionalObjects()
	{
		return this.additionalObjects;
	}
	
}
