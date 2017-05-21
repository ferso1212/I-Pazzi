package it.polimi.ingsw.ps21.model;

public class MultipleProductionSpace extends WorkSpace {
	
	private FamilyMember[] occupants;

	public MultipleProductionSpace(Player occupant, int requirement, ImmProperties instantBonus, WorkSpace workSpace,
			FamilyMember[] occupants) {
		super(occupant, requirement, instantBonus, workSpace);
		this.occupants = occupants;
	}

	public FamilyMember[] getOccupants() {
		return occupants;
	}
	
	public boolean occupy (Player player){
		
	}

}
