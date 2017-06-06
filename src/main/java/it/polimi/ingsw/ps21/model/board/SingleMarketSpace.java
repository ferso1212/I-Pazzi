package it.polimi.ingsw.ps21.model.board;

import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public class SingleMarketSpace extends Space{
	
	protected FamilyMember occupant;

	public SingleMarketSpace(int diceRequirement, ImmProperties instantBonus) {
		super(diceRequirement, instantBonus);
		this.occupant = null;
	}
	
	public boolean isOccupable(Player player, FamilyMember member) {
		if (occupant == null) {
			return true;
		}
		return false;
	}
	
	public void occupy(Player player, FamilyMember member) throws NotOccupableException{
		if (this.occupant==null){
			this.occupant = member;
		} else throw new NotOccupableException();
	}

	public FamilyMember getOccupant() {
		return occupant;
	}
	
	

}
