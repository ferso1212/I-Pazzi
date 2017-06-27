package it.polimi.ingsw.ps21.client;

import java.io.Serializable;
import java.util.ArrayList;

import it.polimi.ingsw.ps21.model.player.PersonalBonusTile;

public class TileChoiceRequestNetPacket extends NetPacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8069889206492009347L;
	private PersonalBonusTile[] possibleChoices;

	public TileChoiceRequestNetPacket(int messNum, PersonalBonusTile[] possibleChoices) {
		super(messNum);
		this.possibleChoices = possibleChoices;
		this.type=PacketType.TILE_CHOICE;
	}

	public PersonalBonusTile[] getPossibleChoices() {
		return possibleChoices;
	}
	
	

}
