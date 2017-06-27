package it.polimi.ingsw.ps21.model.board;

import java.util.ArrayDeque;
import java.util.Queue;

import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.MembersColor;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class CouncilPalace extends Space {
	
	private int diceMalus;
	private int councilPrivileges;
	private Queue<FamilyMember> occupants;
	
	public CouncilPalace(int diceRequirement, ImmProperties instantBonus, int diceMalus, int councilPrivileges) {
		super(diceRequirement, instantBonus);
		this.councilPrivileges = councilPrivileges;
		this.occupants = new ArrayDeque<>();
		this.diceMalus=diceMalus;
	}

	public int getCouncilPrivileges() {
		return councilPrivileges;
	}

	public Queue<FamilyMember> getOccupants() {
		return occupants;
	}

	@Override
	public void occupy(Player player, FamilyMember member) throws NotOccupableException {
		this.occupants.add(member);
		member.setUsed(true);
	}
	
	public boolean checkPlayer(Player player){
		for (FamilyMember f : this.occupants){
			if (f.getOwnerId() == player.getId())
				return true;
		}
		return false;
	}

	@Override
	public boolean isOccupable(Player player, FamilyMember member) {
		if (member.getColor()== MembersColor.NEUTRAL) return false;
		if (member.getValue()<0) return false;
		else return true;
	}

	@Override
	public void reset() {
		occupants.clear();
		
	}

}
