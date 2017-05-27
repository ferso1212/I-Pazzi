package it.polimi.ingsw.ps21.model.board;

import java.util.Queue;

import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CouncilPalace extends MultipleSpace {
	
	private int councilPrivileges;

	public CouncilPalace(int diceRequirement, ImmProperties instantBonus, Queue<FamilyMember> occupants, int diceMalus,
			MultipleSpaceType type, int councilPrivileges) {
		super(diceRequirement, instantBonus, occupants, diceMalus, type);
		this.councilPrivileges = councilPrivileges;
	}

	public int getCouncilPrivileges() {
		return councilPrivileges;
	}

}
