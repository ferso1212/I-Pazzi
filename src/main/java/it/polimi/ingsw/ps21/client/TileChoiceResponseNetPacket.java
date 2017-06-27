package it.polimi.ingsw.ps21.client;

import java.io.Serializable;

public class TileChoiceResponseNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6788901865009191404L;
	private int chosen;

	public TileChoiceResponseNetPacket(int messNum, int chosen) {
		super(messNum);
		this.chosen = chosen;
		this.type=PacketType.TILE_CHOICE;
	}

	public int getChosen() {
		return chosen;
	}
	
	
}
