package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

import it.polimi.ingsw.ps21.model.player.PlayerColor;

public class PlayerIdNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5988355593379761781L;
	PlayerColor id;

	public PlayerIdNetPacket(int messNum, PlayerColor id) {
		super(messNum);
		this.id = id;
		this.type=PacketType.PLAYER_ID;
	}

	public PlayerColor getId() {
		return id;
	}
	

}
