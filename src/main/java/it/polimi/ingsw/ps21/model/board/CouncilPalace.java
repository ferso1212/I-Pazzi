package it.polimi.ingsw.ps21.model.board;

import java.util.Queue;

import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CouncilPalace extends MultipleSpace {
	
	private int councilPrivileges;

	public CouncilPalace(int diceRequirement, ImmProperties instantBonus, MultipleSpaceType type, int councilPrivileges) {
		super(diceRequirement, instantBonus, 0, type);
		this.councilPrivileges = councilPrivileges;
	}

	public int getCouncilPrivileges() {
		return councilPrivileges;
	}

}
