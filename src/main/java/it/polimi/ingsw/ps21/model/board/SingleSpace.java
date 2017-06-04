package it.polimi.ingsw.ps21.model.board;

import it.polimi.ingsw.ps21.model.player.FamilyMember;
import it.polimi.ingsw.ps21.model.player.Player;
import it.polimi.ingsw.ps21.model.properties.ImmProperties;

public abstract class SingleSpace extends Space {

	protected FamilyMember occupant;

	public SingleSpace(int diceRequirement, ImmProperties instantBonus) {
		super(diceRequirement, instantBonus);
		this.occupant = null;
	}

	public FamilyMember getOccupant() {
		return occupant;
	}

	@Override
	public void occupy(Player player, FamilyMember member) throws NotOccupableException {
		if (occupant == null) {
			this.occupant = member;
			if(member.isUsed()){
				throw new NotOccupableException();//cambiare eccezione in AlreadyUsedException
			}
			member.setUsed(true);
		} else
			throw new NotOccupableException();

	}

	@Override
	public boolean isOccupable(Player player, FamilyMember member) {
		if (occupant == null) {
			return true;
		}
		return false;
	}

}
