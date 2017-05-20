package it.polimi.ingsw.ps21.model;

public class WorkSpace extends Space {
	
	protected WorkSpace workSpace;

	public WorkSpace(Player occupant, int requirement, ImmProperties instantBonus, WorkSpace workSpace) {
		super(occupant, requirement, instantBonus);
		this.workSpace = workSpace;
	}

	public WorkSpace getWorkSpace() {
		return workSpace;
	}

}
