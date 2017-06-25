package it.polimi.ingsw.ps21.model.board;

import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class SingleMarketSpace extends Space{
	
	protected FamilyMember occupant;
	protected int numberOfPrivileges;

	public SingleMarketSpace(int diceRequirement, ImmProperties instantBonus, int numberOfPrivileges) {
		super(diceRequirement, instantBonus);
		this.occupant = null;
		this.numberOfPrivileges = numberOfPrivileges;
	}
	
	public boolean isOccupable(Player player, FamilyMember member) {
		if ((occupant == null) && (member.getValue() >= this.diceRequirement)) {
			return true;
		}
		return false;
	}
	
	public void occupy(Player player, FamilyMember member) throws NotOccupableException{
		if (this.occupant==null){
			this.occupant = member;
			member.setUsed(true);
		} else throw new NotOccupableException();
	}

	public FamilyMember getOccupant() {
		return occupant;
	}

	public int getNumberOfPrivileges() {
		return numberOfPrivileges;
	}
	
	

}
