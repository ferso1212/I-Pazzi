package it.polimi.ingsw.ps21.model.board;

import it.polimi.ingsw.ps21.model.SingleSpaceType;

public class SingleSpace extends Space {

	protected FamilyMember occupant;
	protected SingleSpaceType type;

	public SingleSpace(int diceRequirement, ImmProperties instantBonus, FamilyMember member, SingleSpaceType type) {
		super(diceRequirement, instantBonus);
		this.occupant = member;
		this.type = type;
	}

	public FamilyMember getOccupant() {
		return occupant;
	}

	public SingleSpaceType getType() {
		return type;
	}

	public void occupy(FamilyMember member) throws NotOccupableException {
		if (occupant == null) {
			this.occupant = member;
		} else
			throw new NotOccupableException();

	}

	@Override
	public boolean isOccupable(FamilyMember member) {
		if (occupant == null) {
			return true;
		}
		return false;
	}

}
