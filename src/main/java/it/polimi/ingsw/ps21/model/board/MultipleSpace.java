package it.polimi.ingsw.ps21.model.board;

import java.util.Queue;

import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class MultipleSpace extends Space{
	
	protected Queue<FamilyMember> occupants;
	protected int diceMalus;
	protected MultipleSpaceType type;
	
	

	public MultipleSpace(int diceRequirement, ImmProperties instantBonus, Queue<FamilyMember> occupants, int diceMalus,
			MultipleSpaceType type) {
		super(diceRequirement, instantBonus);
		this.occupants = occupants;
		this.diceMalus = diceMalus;
		this.type = type;
	}

	public Queue<FamilyMember> getOccupants() {
		return occupants;
	}

	@Override
	public boolean isOccupable(FamilyMember member) {
		if ((!this.type.equals(MultipleSpaceType.COUNCIL)) && (occupants.contains(member))){
			return false;
		}
		return true;
	}

	@Override
	public void occupy(FamilyMember famMember) throws NotOccupableException{
		occupants.add(famMember);
		if(famMember.isUsed()){
			throw new NotOccupableException();//cambiare eccezione in AlreadyUsedException
		}
		famMember.setUsed(true);
	}

	
	
	 
}
