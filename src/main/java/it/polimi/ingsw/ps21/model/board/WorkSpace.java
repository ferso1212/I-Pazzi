package it.polimi.ingsw.ps21.model.board;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class WorkSpace extends Space{
	
	protected WorkType workType;

	public WorkSpace(int diceRequirement, ImmProperties instantBonus, WorkType workType) {
		super(diceRequirement, instantBonus);
		this.workType = workType;
	}

	public WorkType getWorkType() {
		return workType;
	}

	@Override
	public boolean isOccupable(Player player, FamilyMember member) {
		return false;
	}

	@Override
	public void occupy(Player player, FamilyMember member) throws NotOccupableException {		
	}
	

}
