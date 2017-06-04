package it.polimi.ingsw.ps21.model.board;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class SingleWorkSpace extends SingleSpace implements WorkInterface{
	
	public WorkType workType;

	public SingleWorkSpace(int diceRequirement, ImmProperties instantBonus, SingleSpaceType type, WorkType workType) {
		super(diceRequirement, instantBonus, type);
		this.workType = workType;
	}

	@Override
	public WorkType getWorkType() {
		return this.workType;
	}

	@Override
	public void occupy(Player player, FamilyMember member) throws NotOccupableException {
		// TODO Auto-generated method stub
		super.occupy(player, member);
	}

	@Override
	public boolean isOccupable(Player player, FamilyMember member) {
		if (this.occupant == null)
			return true;
		return false;
	}
	
	

}
