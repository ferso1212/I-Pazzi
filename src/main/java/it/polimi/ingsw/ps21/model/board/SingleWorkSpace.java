package it.polimi.ingsw.ps21.model.board;

import it.polimi.ingsw.ps21.model.actions.WorkType;
import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class SingleWorkSpace extends WorkSpace{

	protected FamilyMember occupant;

	public SingleWorkSpace(int diceRequirement, ImmProperties instantBonus, WorkType workType) {
		super(diceRequirement, instantBonus, workType);
		this.occupant = null;
	}
	
	@Override
	public boolean isOccupable(Player player, FamilyMember member) {
		if ((occupant == null)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void occupy(Player player, FamilyMember member) throws NotOccupableException{
		if (this.occupant==null){
			this.occupant = member;
			member.setUsed(true);
		} else throw new NotOccupableException();
	}

	public FamilyMember getOccupant() {
		return occupant;
	}

	@Override
	public void reset() {
		this.occupant=null;
		
	}

	

}
