package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

import it.polimi.ingsw.ps21.controller.MatchData;

public class ViewUpdateRequestNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 868840968479150607L;
	private MatchData match;


	public ViewUpdateRequestNetPacket(int messNum, MatchData match) {
		super(messNum);
		this.match = match;
		this.type=PacketType.VIEW_UPDATE_REQUEST;
	}


	public MatchData getMatch() {
		return match;
	}
	
	
}
