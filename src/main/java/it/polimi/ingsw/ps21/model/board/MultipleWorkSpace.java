package it.polimi.ingsw.ps21.model.board;

import java.util.Queue;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class MultipleWorkSpace extends MultipleSpace implements WorkInterface{
	
	private WorkType workType;

	public MultipleWorkSpace(int diceRequirement, ImmProperties instantBonus, int diceMalus,
			Queue<FamilyMember> occupants, WorkType workType) {
		super(diceRequirement, instantBonus, diceMalus, occupants);
		this.workType = workType;
	}

	@Override
	public boolean isOccupable(Player player, FamilyMember member) {
		if ((occupants.contains(member)) || (this.containPlayer(player))){
			return false;
		}
		return true;
	}
	
	@Override
	public void occupy(Player palyer, FamilyMember famMember) {
		occupants.add(famMember);
		famMember.setUsed(true);
	}
	
	public WorkType getWorkType(){
		return this.workType;
	}

}
