package it.polimi.ingsw.ps21.model.board;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class AdvSingleWorkSpace extends AdvSingleSpace implements WorkInterface{
	
	public WorkType workType;

	public AdvSingleWorkSpace(int diceRequirement, ImmProperties instantBonus, WorkType workType) {
		super(diceRequirement, instantBonus);
		this.workType = workType;
	}



	public WorkType getWorkType() {
		return workType;
	}

}
