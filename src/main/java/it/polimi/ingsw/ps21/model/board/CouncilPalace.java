package it.polimi.ingsw.ps21.model.board;

import java.util.Queue;

import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CouncilPalace extends MultipleSpace {
	
	private int councilPrivileges;

	public CouncilPalace(int diceRequirement, ImmProperties instantBonus, int diceMalus, int councilPrivileges) {
		super(diceRequirement, instantBonus, diceMalus, null);
		this.councilPrivileges = councilPrivileges;
	}

	public int getCouncilPrivileges() {
		return councilPrivileges;
	}

	@Override
	public boolean isOccupable(Player player, FamilyMember member) {
		if(member.getColor() != MembersColor.NEUTRAL)
			return true;
		return false;
	}

	@Override
	public void occupy(Player player, FamilyMember member) throws NotOccupableException {
		this.occupants.add(member);
		
	}
	
	

}
