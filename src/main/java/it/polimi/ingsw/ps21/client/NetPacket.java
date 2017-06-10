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
	 * Builds a network package.
	 * @param type the type of the packet
	 * @param o	the object to send
	 * @param num
	 */
	public NetPacket(PacketType type, Object o, int messNum) {
		super();
		this.type = type;
		this.o = o;
		this.num = messNum;
		this.additionalObjects=null;
	}

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

	
	public PacketType getType() {
		return type;
	}


	public Object getObject() {
		return o;
	}


	public int getNum() {
		return num;
	}

	public Object getAdditionalObject(int index)
	{
		return this.additionalObjects[index];
	}
	
	public Object[] getAdditionalObjects()
	{
		return this.additionalObjects;
	}
	
}
