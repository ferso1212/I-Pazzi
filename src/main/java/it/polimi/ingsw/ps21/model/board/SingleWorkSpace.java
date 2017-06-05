package it.polimi.ingsw.ps21.model.board;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class SingleWorkSpace extends SingleSpace implements WorkInterface{
	
	protected WorkType workType;

	public SingleWorkSpace(int diceRequirement, ImmProperties instantBonus, WorkType workType) {
		super(diceRequirement, instantBonus);
		this.workType = workType;
	}

	@Override
	public WorkType getWorkType() {
		return this.workType;
	}	
	

}
