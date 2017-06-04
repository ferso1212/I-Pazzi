package it.polimi.ingsw.ps21.model.board;

import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class AdvSingleMarketSpace extends AdvSingleSpace{

	public AdvSingleMarketSpace(int diceRequirement, ImmProperties instantBonus, SingleSpaceType type,
			FamilyMember otherOccupant) {
		super(diceRequirement, instantBonus, type, otherOccupant);
	}
	
	

}
