package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

public class MatchStartedNetPacket extends NetPacket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -770627614747551714L;

	public MatchStartedNetPacket(int messNum) {
		super(messNum);
		this.type=PacketType.MATCH_STARTED_NOTIFICATION;
	}

}
