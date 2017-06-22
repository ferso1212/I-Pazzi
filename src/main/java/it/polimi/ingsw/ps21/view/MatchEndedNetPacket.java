package it.polimi.ingsw.ps21.view;

import java.io.Serializable;

import it.polimi.ingsw.ps21.client.NetPacket;
import it.polimi.ingsw.ps21.client.PacketType;

public class MatchEndedNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2672148139436038792L;
	private EndData data;

	public EndData getData() {
		return data;
	}

	public MatchEndedNetPacket(int messNum, EndData data) {
		super(messNum);
		this.data = data;
		this.type=PacketType.MATCH_END;
	}
	
	
}
